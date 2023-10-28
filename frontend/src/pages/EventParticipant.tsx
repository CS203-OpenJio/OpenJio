import React, { useState, useMemo, useCallback, useRef, useEffect } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "src/components/ui/table";
import { Input, Button } from '@mui/material';
import { CSVLink } from "react-csv";
import NavBar from '../components/NavBar';
import { getParticipants,getAcceptedParticipants,allocateSlots } from '../utils/CreatedEventController';
import { useNavigate, useSearchParams } from "react-router-dom";

interface Participant {
    id: number;
    name: string;
    email: string;
    phone: string;
    matricNo: string;
}

const participantsData: Participant[] = [
    { id: 1, name: 'no one found', email: 'no one found', phone: 'no one found', matricNo: 'no one found' },
];

const EventParticipant = () => {
    const [searchParams] = useSearchParams();
    const eventId = searchParams.get("id");
    const [participants, setParticipants] = useState(participantsData);
    const [searchTerm, setSearchTerm] = useState('');
    const [sortConfig, setSortConfig] = useState<{ key: string; direction: string }>({ key: '', direction: '' });

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

    useEffect(() => {
        if (eventId) {
            getAcceptedParticipants(eventId).then((eventParticipants) => {
                setParticipants(eventParticipants);
            });
        } else {
            throw new Error("Event not found.");
        }
    }, []);

    const handleAllocate = async () => {
        await allocateSlots(eventId??'');
        await getAcceptedParticipants(eventId??'').then((eventParticipants) => {
            setParticipants(eventParticipants);
            console.log(eventParticipants);
        });
    }


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

    return (
        <div>
            <NavBar />
            <div className="container mx-auto max-w-4xl mt-[80px]">
                <div className="py-8">
                    <div className="flex flex-row mb-1 sm:mb-0 justify-between items-center w-[90%] p-4">
                        <h2 className="text-2xl leading-tight font-ibm-plex-mono">Participants</h2>
                        <Input placeholder="Search" className="mr-2" value={searchTerm} onChange={handleSearch} />
                        <CSVLink {...handleExport()} filename={'participants.csv'} className="font-bold text-sm font-ibm-plex-mono px-3 py-2 bg-green-600 rounded-md shadow-lg text-white no-underline hover:bg-green-700">
                            Export All
                        </CSVLink>
                        <div className='font-bold text-sm font-ibm-plex-mono px-3 py-2 bg-red-600 rounded-md shadow-lg text-white hover:bg-red-700 hover:cursor-pointer'
                        onClick={handleAllocate}>
                            Allocate
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
                                    <TableHead className="hover:cursor-pointer" onClick={() => handleSort('phone')}>Matric No.</TableHead>
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
