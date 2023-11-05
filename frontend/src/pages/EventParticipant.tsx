import React, { useState, useMemo, useCallback, useRef, useEffect } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "src/components/ui/table";
import { Input, Button, ToggleButtonGroup, ToggleButton, ButtonGroup, Tooltip } from '@mui/material';
import { CSVLink } from "react-csv";
import NavBar from '../components/NavBar';
import { getParticipants, getStatusParticipants, allocateSlots, closeEvent } from '../utils/CreatedEventController';
import { getEvents } from '../utils/EditEventController';
import { useNavigate, useSearchParams } from "react-router-dom";
import { toast } from 'react-toastify';
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';
import { Check, ChevronsUpDown } from "lucide-react"
import { cn } from "../lib/utils"
import {
    Command,
    CommandEmpty,
    CommandGroup,
    CommandInput,
    CommandItem,
} from "../components/ui/command"
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "../components/ui/popover"
import { string } from 'zod';

interface Participant {
    id: number;
    name: string;
    email: string;
    phone: string;
    matricNo: string;
    smuCreditScore: string;
}

const participantsData: Participant[] = [
    { id: 1, name: 'no one found', email: 'no one found', phone: 'no one found', matricNo: 'no one found', smuCreditScore: "no one" },
];


const EventParticipant = () => {
    const [searchParams] = useSearchParams();
    const eventId = searchParams.get("id");
    const [participants, setParticipants] = useState(participantsData);
    const [searchTerm, setSearchTerm] = useState('');
    const [sortConfig, setSortConfig] = useState<{ key: string; direction: string }>({ key: '', direction: '' });
    const [alignment, setAlignment] = React.useState('web');
    const [completed, setCompleted] = useState(false);
    const [eventName, setEventName] = useState("");
    const [value, setValue] = useState<string | null>(null);


    const handleChange = async (
        event: React.MouseEvent<HTMLElement>,
        newAlignment: string,
    ) => {
        setAlignment(newAlignment);
        if (eventId) {
            if (newAlignment === 'ALL') {
                await getParticipants(eventId ?? '').then((eventParticipants) => {
                    setParticipants(eventParticipants);
                });
            } else if (newAlignment === 'ACCEPTED' || newAlignment === 'PENDING' || newAlignment === 'REJECTED') {
                await getStatusParticipants(eventId, newAlignment).then((eventParticipants) => {
                    setParticipants(eventParticipants);
                });
            } else {
                setAlignment("ALL");
                await getParticipants(eventId ?? '').then((eventParticipants) => {
                    setParticipants(eventParticipants);
                });
            }
        }

    };

    const handleSearch = useCallback((event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
    }, []);

    const handleSort = useCallback((key: string) => {
        let direction = 'ascending';
        if (sortConfig.key === key && sortConfig.direction === 'ascending') {
            direction = 'descending';
        }
        setSortConfig({ key, direction });
    }, [sortConfig]);

    const updateCompleted = () => {
        if (eventId) {
            try {
                getEvents(eventId).then((newEvent) => {
                    setEventName(newEvent.name);
                    setCompleted(newEvent.completed);
                });
            } catch (error) {
                toast.error("Error updating completed.");
            }
        } else {
            toast.error("Event not found.");
        }
    }

    useEffect(() => {
        if (eventId) {
            updateCompleted();
            getParticipants(eventId).then((eventParticipants) => {
                setParticipants(eventParticipants);
                handleChange({} as React.MouseEvent<HTMLElement>, 'ALL');
            });
        } else {
            toast.error("Event not found.");
        }
    }, []);

    const handleAllocate = async () => {
        if (eventId) {
            try {
                await allocateSlots(eventId, value);
                await getStatusParticipants(eventId, "ACCEPTED").then((eventParticipants) => {
                    setParticipants(eventParticipants);
                    handleChange({} as React.MouseEvent<HTMLElement>, 'ACCEPTED');
                });
            } catch (error: any) {
                console.log(error)
                toast.error(error.message);
            }

        }

    };


    const handleExport = useCallback(() => {
        const headers = [
            { label: 'ID', key: 'id' },
            { label: 'Name', key: 'name' },
            { label: 'Email', key: 'email' },
            { label: 'Phone', key: 'phone' },
        ];
        const csvData = participants.map((participant) => ({
            id: participant.id,
            name: participant.name,
            email: participant.email,
            phone: participant.phone,
            matricNo: participant.matricNo,
        }));
        return { headers, data: csvData };
    }, [participants]);

    const sortedParticipants = useMemo(() => {
        let sortableParticipants = [...participants];
        if (sortConfig.key !== '') {
            sortableParticipants.sort((a, b) => {
                const key = sortConfig.key as keyof Participant;
                if (a[key] < b[key]) {
                    return sortConfig.direction === 'ascending' ? -1 : 1;
                }
                if (a[key] > b[key]) {
                    return sortConfig.direction === 'ascending' ? 1 : -1;
                }
                return 0;
            });
        }
        return sortableParticipants;
    }, [participants, sortConfig]);

    const filteredParticipants = useMemo(() => {
        let filteredParticipants = [...sortedParticipants];
        if (searchTerm !== '') {
            filteredParticipants = filteredParticipants.filter((participant) =>
                participant.name.toLowerCase().includes(searchTerm.toLowerCase())
            );
        }
        return filteredParticipants;
    }, [searchTerm, sortedParticipants]);

    async function handleClose(): Promise<void> {
        if (eventId) {
            try {
                await closeEvent(eventId)
                toast.success("Event closed.");
                updateCompleted();
            } catch (error) {
                toast.error("Error closing event.");
            }
        } else {
            toast.error("Event not found.");
        }
    }

    return (
        <div>
            <NavBar />
            <div className="container mx-auto max-w-4xl mt-[80px]">
                <div className="py-8">
                    <h2 className="text-2xl leading-tight font-ibm-plex-mono">Participants<span className='text-sm'> of <span className='underline tracking-tight'>{eventName}</span></span></h2>
                    <div className="flex flex-row mb-1 sm:mb-0 justify-between">
                        <div className='flex-col'>
                            <ToggleButtonGroup
                                className='h-8'
                                color="primary"
                                value={alignment}
                                exclusive
                                onChange={handleChange}
                                aria-label="Platform"
                            >
                                <ToggleButton value="ALL">All</ToggleButton>
                                <ToggleButton value="ACCEPTED">Accepted</ToggleButton>
                                <ToggleButton value="PENDING">Pending</ToggleButton>
                                <ToggleButton value="REJECTED">Rejected</ToggleButton>
                            </ToggleButtonGroup>
                            <Input placeholder="Search" className="ml-2" value={searchTerm} onChange={handleSearch} />
                        </div>

                        <div className='flex flex-col items-end'>
                            <ButtonGroup variant="contained" aria-label="outlined primary button group">
                                <Button
                                    className='bg-green-500'
                                    color="success">
                                    <CSVLink {...handleExport()} filename={'participants.csv'} className="text-white no-underline flex">
                                        <CloudDownloadIcon /><span className='ml-1'>.CSV</span>
                                    </CSVLink>

                                </Button>
                                <Button onClick={handleClose} disabled={completed} color="error">{completed ? "event closed" : "close event"}</Button>
                            </ButtonGroup>
                            <ButtonGroup variant="contained" aria-label="outlined primary button group" >
                                <Popover>
                                    <PopoverTrigger asChild>
                                        <Button
                                            role="combobox"
                                            className={cn(
                                                "w-[200px] justify-between hover:cursor-pointer",
                                                !value && "text-muted-foreground"
                                            )}
                                        >
                                            {value
                                                ? languages.find(
                                                    (language) => language.value === value
                                                )?.label
                                                : "Select Algorithm"}
                                            <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                                        </Button>
                                    </PopoverTrigger>
                                    <PopoverContent className="w-[300px] p-0 mt-[-4px]">
                                        <Command>
                                            <CommandGroup>
                                                {languages.map((language) => (
                                                    <Tooltip title={language.desc} placement="right">
                                                        <CommandItem
                                                            value={language.label}
                                                            className="font-ibm-plex-mono hover:cursor-pointer"
                                                            key={language.value}
                                                            onSelect={() => {
                                                                setValue(language.value)
                                                            }}
                                                        >
                                                            <Check
                                                                className={cn(
                                                                    "mr-2 h-4 w-4",
                                                                    language.value === value
                                                                        ? "opacity-100"
                                                                        : "opacity-0"
                                                                )}
                                                            />
                                                            {language.label}
                                                        </CommandItem>
                                                    </Tooltip>
                                                ))}
                                            </CommandGroup>
                                        </Command>
                                    </PopoverContent>
                                </Popover>
                                <Button onClick={handleAllocate} disabled={completed}>Allocate</Button>
                            </ButtonGroup>
                        </div>

                    </div>


                    <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto font-ibm-plex-mono">
                        <Table>
                            <TableHeader>
                                <TableRow>
                                    <TableHead className="hover:cursor-pointer" onClick={() => handleSort('id')}>ID</TableHead>
                                    <TableHead className="hover:cursor-pointer" onClick={() => handleSort('name')}>Name</TableHead>
                                    <TableHead className="hover:cursor-pointer" onClick={() => handleSort('email')}>Email</TableHead>
                                    <TableHead className="hover:cursor-pointer" onClick={() => handleSort('phone')}>Phone</TableHead>
                                    <TableHead className="hover:cursor-pointer" >Matric No.</TableHead>
                                    <TableHead className="hover:cursor-pointer" onClick={() => handleSort('smuCreditScore')}>Student Score</TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {filteredParticipants.map((participant) => (
                                    <TableRow key={participant.id}>
                                        <TableCell>{participant.id}</TableCell>
                                        <TableCell>{participant.name}</TableCell>
                                        <TableCell>{participant.email}</TableCell>
                                        <TableCell>{participant.phone}</TableCell>
                                        <TableCell>{participant.matricNo}</TableCell>
                                        <TableCell>{participant.smuCreditScore}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </div>
                </div>
            </div>
        </div>

    );
};

export default EventParticipant;

const languages = [
    { label: "Normal Queue", value: "FCFS", desc: "First Come First Serve" },
    { label: "Random", value: "Random", desc: "Participants are randomly chosen" },
    { label: "Priority Queue", value: "Score", desc: "Participants are chosen from a queue, but weighted by their event attendance rate" },
    { label: "Weighted Random", value: "Weighted Random", desc: "Participants are chosen randomly, participants with a history of successfully attending events have a higher chance of being chosen" },
] as const
