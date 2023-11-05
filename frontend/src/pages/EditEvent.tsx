import React, { useState, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import NavBar from "../components/NavBar";
import { getEvents, handleChangeEvent, deleteEvent } from "../utils/EditEventController";
import { toast } from "react-toastify";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { DateRange } from "react-day-picker";
import {
    Form,
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "../components/ui/form"
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "../components/ui/popover"
import { Input } from "../components/ui/input"
import { Button } from "../components/ui/button"
import { format } from "date-fns"
import { Calendar as CalendarIcon } from "lucide-react"
import { Check, ChevronsUpDown } from "lucide-react"
import { cn } from "../lib/utils"
import { Calendar } from "../components/ui/calendar"
import { Textarea } from "../components/ui/textarea";
import { Command, CommandGroup, CommandItem } from "../components/ui/command";
import { Switch } from "../components/ui/switch";
import { Tooltip } from "@mui/material";
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from "../components/ui/dialog";

const EditEventPage: React.FC = () => {
    const navigate = useNavigate();
    const [eventData, setEventData] = React.useState<any>({});
    const [searchParams] = useSearchParams();
    const eventId = searchParams.get("id");
    const [date, setDate] = React.useState<DateRange | undefined>();
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (eventId) {
            getEvents(eventId).then((newEvent) => {
                setLoading(true);
                setEventData({ ...newEvent });
                setDate({ from: new Date(newEvent.startDateTime), to: new Date(newEvent.endDateTime) });
            });
        } else {
            throw new Error("Event not found.");
        }
    }, []);
    useEffect(() => {
        if (eventData) {
            form.reset({
                name: eventData.name,
                capacity: eventData.capacity?.toString(),
                venue: eventData.venue,
                date: date,
                image: null,
                description: eventData.description,
                algo: eventData.algo,
                visible: eventData.visible,
                minScore: eventData.minScore,
            });
            setLoading(false);
        }
    }, [eventData]);

    const form = useForm<z.infer<typeof FormSchema>>({
        resolver: zodResolver(FormSchema),
        defaultValues: {
            name: eventData?.name ?? '',
            capacity: eventData?.capacity?.toString() ?? '',
            venue: eventData?.venue ?? '',
            date: date ? { from: date.from, to: date.to } : null,
            image: eventData?.image ?? null,
            description: eventData?.description ?? '',
            algo: eventData?.algo ?? '',
            visible: eventData?.visible ?? false,
            minScore: eventData?.minScore ?? 80,
        },
    });

    function onSubmit(data: z.infer<typeof FormSchema>) {
        let hasUpdates = false;
        let updatedData: any = { ...data };
        const updatedFields: any = {};
        // if image is in data, delete it from data
        // UPDATE WHEN IMAGES ARE FIXED

        if ("date" in updatedData) {
            let date = updatedData["date"] as { from?: string, to?: string };
            delete updatedData["date"];
            //format date below
            let dateTo = new Date(date?.to ?? "");
            var userTimezoneOffset1 = dateTo.getTimezoneOffset() * 60000;
            dateTo = new Date(dateTo.getTime() - userTimezoneOffset1);
            let dateFrom = new Date(date?.from ?? "");
            var userTimezoneOffset2 = dateFrom.getTimezoneOffset() * 60000;
            dateFrom = new Date(dateFrom.getTime() - userTimezoneOffset2);
            updatedData = {
                ...updatedData,
                startDateTime: dateFrom.toISOString().split('T')[0] + 'T00:00:00',
                endDateTime: dateTo.toISOString().split('T')[0] + 'T00:00:00'
            }
        }

        Object.keys(updatedData).forEach((key: string) => {
            if (key === "image") {
                hasUpdates = true;
                updatedFields[key] = updatedData[key];
            } else if (updatedData[key] != eventData[key]) {
                hasUpdates = true;
                updatedFields[key] = updatedData[key];
            }
        });

        if (hasUpdates && eventId) {
            // create a new event object with only the fields
            // that have been updated by checking with eventData
            if ("startDateTime" in updatedData) {
                let date = updatedData["startDateTime"];
                delete updatedData["startDateTime"];
                updatedFields["startDateTime"] = date + ".000Z";
            }
            if ("endDateTime" in updatedData) {
                let date = updatedData["endDateTime"];
                delete updatedData["endDateTime"];
                updatedFields["endDateTime"] = date + ".000Z";
            }
            const updatedEvent = { ...updatedFields };
            try {
                console.log("below")
                console.log(updatedEvent);
                handleChangeEvent(eventId, updatedEvent);
                // navigate("/createdevents")
            }
            catch (error) {
                console.log(error);
                toast.error("Error updating event details!");
            }
        } else {
            toast.error("No changes made.")
        }
    }

    return (
        <div className="w-screen">
            <NavBar />
            <div className="container mx-auto mt-[80px]">
                <h1 className="text-2xl font-bold mb-4 font-ibm-plex-mono">Edit Event</h1>
            </div>
            <div className="m-auto w-2/3 gap-20">
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                        <FormField
                            control={form.control}
                            name="name"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel className="font-ibm-plex-mono">Event Name</FormLabel>
                                    <FormControl>
                                        <Input className="bg-white" placeholder="Event Name" {...field} />
                                    </FormControl>
                                    <FormDescription className="font-ibm-plex-mono">
                                        This is your event’s name
                                    </FormDescription>
                                    <FormMessage className="font-ibm-plex-mono" />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="capacity"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel className="font-ibm-plex-mono">Event Capacity</FormLabel>
                                    <FormControl>
                                        <Input className="bg-white" placeholder="Event Capacity" {...field} />
                                    </FormControl>
                                    <FormDescription className="font-ibm-plex-mono">
                                        This is your event’s capacity
                                    </FormDescription>
                                    <FormMessage className="font-ibm-plex-mono" />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="venue"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel className="font-ibm-plex-mono">Event Venue</FormLabel>
                                    <FormControl>
                                        <Input className="bg-white" placeholder="Event Venue" {...field} />
                                    </FormControl>
                                    <FormDescription className="font-ibm-plex-mono">
                                        This is your event’s location
                                    </FormDescription>
                                    <FormMessage className="font-ibm-plex-mono" />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="date"
                            render={({ }) => (
                                <FormItem className="flex flex-col">
                                    <FormLabel className="font-ibm-plex-mono">Event Duration</FormLabel>
                                    <div className={cn("grid gap-2")}>
                                        <Popover>
                                            <PopoverTrigger asChild>
                                                <Button
                                                    id="date"
                                                    variant={"outline"}
                                                    className={cn(
                                                        "w-[300px] justify-start text-left font-normal hover:cursor-pointer bg-white",
                                                        !date && "text-muted-foreground"
                                                    )}
                                                >
                                                    <CalendarIcon className="mr-2 h-4 w-4" />
                                                    {date?.from ? (
                                                        date.to ? (
                                                            <>
                                                                {format(date.from, "LLL dd, y")} -{" "}
                                                                {format(date.to, "LLL dd, y")}
                                                            </>
                                                        ) : (
                                                            format(date.from, "LLL dd, y")
                                                        )
                                                    ) : (
                                                        <span>Pick a date</span>
                                                    )}
                                                </Button>
                                            </PopoverTrigger>
                                            <PopoverContent className="w-auto p-0 font-ibm-plex-mono" align="start">
                                                <Calendar
                                                    initialFocus
                                                    mode="range"
                                                    defaultMonth={date?.from}
                                                    selected={date}
                                                    onSelect={(newDate) => {
                                                        setDate(newDate);
                                                        form.setValue("date", newDate);  // update the form state
                                                    }}
                                                    numberOfMonths={2}
                                                />
                                            </PopoverContent>
                                        </Popover>
                                    </div>
                                    <FormDescription className="font-ibm-plex-mono">
                                        This is your event’s duration
                                    </FormDescription>
                                    <FormMessage className="font-ibm-plex-mono" />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="algo"
                            render={({ field }) => (
                                <FormItem className="flex flex-col font-ibm-plex-mono">
                                    <FormLabel>Event Algorithm</FormLabel>
                                    <Popover>
                                        <PopoverTrigger asChild>
                                            <FormControl>
                                                <Button
                                                    variant="outline"
                                                    role="combobox"
                                                    className={cn(
                                                        "w-[300px] justify-between hover:cursor-pointer bg-white",
                                                        !field.value && "text-muted-foreground"
                                                    )}
                                                >
                                                    {field.value
                                                        ? languages.find(
                                                            (language) => language.value === field.value
                                                        )?.label
                                                        : "Select Algorithm"}
                                                    <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                                                </Button>
                                            </FormControl>
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
                                                                    form.setValue("algo", language.value)
                                                                }}
                                                            >
                                                                <Check
                                                                    className={cn(
                                                                        "mr-2 h-4 w-4",
                                                                        language.value === field.value
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
                                    <FormDescription>
                                        This chooses the way your slots are distributed
                                    </FormDescription>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />

                        <FormField
                            control={form.control}
                            name="visible"
                            render={({ field }) => (
                                <FormItem className="flex flex-col rounded-lg font-ibm-plex-mono">
                                    <FormLabel className="text-base">
                                        Event Visibility
                                    </FormLabel>
                                    <FormControl>
                                        <div className="flex flex-row">
                                            <Switch
                                                checked={field.value}
                                                onCheckedChange={field.onChange}
                                            />
                                            <div className="ml-6">
                                                {field.value ? "Event is visible!" : "Event is not visible."}
                                            </div>
                                        </div>
                                    </FormControl>
                                    <FormDescription>
                                        Let your event be visible?
                                    </FormDescription>

                                </FormItem>
                            )}
                        />

                        <FormField
                            control={form.control}
                            name="description"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel className="font-ibm-plex-mono">Event Description</FormLabel>
                                    <FormControl>
                                        <Textarea className="bg-white h-96 w-5/6" placeholder="Type your description here." {...field} />
                                    </FormControl>
                                    <FormMessage className="font-ibm-plex-mono" />
                                </FormItem>
                            )}
                        />
                        <FormField
                            name="image"
                            render={({ field }) => (
                                <FormItem className="flex flex-col">
                                    <FormLabel className="font-ibm-plex-mono">Event Image</FormLabel>
                                    <Input
                                        id="picture"
                                        className="bg-white w-96 hover:cursor-pointer"
                                        type="file"
                                        onChange={(e) => {
                                            e.target.files && field.onChange(e.target.files[0]);
                                        }}
                                    />
                                    <FormDescription className="font-ibm-plex-mono">
                                        This is your event’s image (optional)
                                    </FormDescription>
                                    <FormMessage className="font-ibm-plex-mono" />
                                </FormItem>
                            )}
                        />
                        <FormField
                            control={form.control}
                            name="minScore"
                            render={({ field }) => (
                                <FormItem className="flex flex-col font-ibm-plex-mono">
                                    <FormLabel>Event Registration Options</FormLabel>
                                    <Popover>
                                        <PopoverTrigger asChild>
                                            <FormControl>
                                                <Button
                                                    variant="outline"
                                                    role="combobox"
                                                    className={cn(
                                                        "w-[300px] justify-between hover:cursor-pointer bg-white",
                                                        !field.value && "text-muted-foreground"
                                                    )}
                                                >
                                                    {field.value
                                                        ? minScoreFilter.find(
                                                            (minScoreFilter) => minScoreFilter.value === field.value
                                                        )?.label
                                                        : "Select Option"}
                                                    <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                                                </Button>
                                            </FormControl>
                                        </PopoverTrigger>
                                        <PopoverContent className="w-[300px] p-0 mt-[-4px]">
                                            <Command>
                                                <CommandGroup>
                                                    {minScoreFilter.map((minScoreFilter) => (
                                                        <Tooltip title={minScoreFilter.desc} placement="right">
                                                            <CommandItem
                                                                value={minScoreFilter.label}
                                                                className="font-ibm-plex-mono hover:cursor-pointer"
                                                                key={minScoreFilter.value}
                                                                onSelect={() => {
                                                                    form.setValue("minScore", minScoreFilter.value)
                                                                }}
                                                            >
                                                                <Check
                                                                    className={cn(
                                                                        "mr-2 h-4 w-4",
                                                                        minScoreFilter.value === field.value
                                                                            ? "opacity-100"
                                                                            : "opacity-0"
                                                                    )}
                                                                />
                                                                {minScoreFilter.label}
                                                            </CommandItem>
                                                        </Tooltip>
                                                    ))}
                                                </CommandGroup>
                                            </Command>
                                        </PopoverContent>
                                    </Popover>
                                    <FormDescription>
                                        Choose the type or users able to participate in your event.
                                    </FormDescription>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                        <div className="flex flex-row justify-between w-[100%]">
                            <DeleteButton id={eventId} />
                            <Button type="submit" className="hover:cursor-pointer">Confirm Changes</Button>
                        </div>
                    </form>
                </Form>
            </div>
        </div>
    );
    function DeleteButton({ id }: { id: any }) {
        const navigate = useNavigate();
        async function handleClick() {
            try {
                await deleteEvent(id);
                navigate("/createdevents");
            } catch (err: any) {
                toast.error(err.message);
            }
        }

        return (
            <div>
                <Dialog>
                    <DialogTrigger asChild>
                        <Button variant="destructive" className="hover:cursor-pointer">Delete Event</Button>
                    </DialogTrigger>
                    <DialogContent className="sm:max-w-[425px]">
                        <DialogHeader>
                            <DialogTitle className="font-ibm-plex-mono tracking-wide">
                                Delete Event?
                            </DialogTitle>
                        </DialogHeader>
                        <DialogDescription className="font-ibm-plex-mono">
                            This action cannot be undone. This will permanently delete the event
                            and remove it's data from our servers.
                        </DialogDescription>
                        <DialogFooter>
                            <Button
                                variant="destructive"
                                onClick={handleClick}
                                className="hover:cursor-pointer font-ibm-plex-mono"
                            >
                                Confirm
                            </Button>
                        </DialogFooter>
                    </DialogContent>
                </Dialog>
            </div>
        );
    }
};

const FormSchema = z.object({
    name: z.string().min(3, {
        message: "Event Name must be at least 3 characters.",
    }),
    capacity: z.string().refine((value) => {
        // Ensure it's a numeric string
        const numericValue = Number(value);
        if (isNaN(numericValue)) {
            return false; // Not a number
        }
        // Limit to a maximum of 1000
        return numericValue >= 1 && numericValue <= 10000;
    }, {
        message: "Capacity must be a number between 1 and 10000.",
    }),
    venue: z.string().min(4, {
        message: "Venue must be at least 4 characters.",
    }),
    date: z.custom((value: unknown) => {
        const dateRange = value as { from?: Date; to?: Date };
        // Perform your custom validation for DateRange here
        // You can check if value is a valid DateRange or not
        // Return true if it's valid, or false if it's not
        return dateRange?.from != null && dateRange?.to != null;
    }, {
        message: "Please set a date range.",
    }),
    description: z.string().refine((value) => {
        // Split the description into words and filter out empty strings
        const words = value.trim().split(/\s+/).filter(Boolean);
        // Check if the number of words is at least 10
        return words.length >= 10;
    }, {
        message: "Description must have at least 10 words.",
    }),
    image: z.custom((value) => {
        // If value is null, it means no file was selected
        if (value == null) {
            return true;
        }
        if (!(value instanceof File)) {
            return false;
        }
        // validation here
        const maxSize = 5 * 1024 * 1024;
        if (value.size > maxSize) {
            return false;
        }

        const allowedMimeTypes = ["image/jpeg", "image/png"];
        if (!allowedMimeTypes.includes(value.type)) {
            return false;
        }

        return true;
    }, {
        message: "Please choose an Image! Supprted file formats : JPEG, PNG (Max. size 5Mb)",
    }),
    algo: z.string({
        required_error: "Please select an algorithm.",
    }),
    visible: z.boolean().default(false),
    minScore: z
        .number()
        .min(0, {
            message: "Minimum score must be greater than or equal to 0.",
        })
        .max(100, {
            message: "Maximum score must be less than or equal to 100.",
        }),
})

const languages = [
    { label: "Normal Queue", value: "FCFS", desc: "First Come First Serve" },
    { label: "Random Selection", value: "Random", desc: "Participants are randomly chosen" },
    { label: "Weighted Queue Selection", value: "Score", desc: "Participants are chosen from a queue, but weighted by their event attendance rate" },
    { label: "Weighted Random Selection", value: "Weighted Random", desc: "Participants are chosen randomly, participants with a history of successfully attending events have a higher chance of being chosen" },
] as const
const minScoreFilter = [
    { label: "Normal", value: 0, desc: "All are allowed to register" },
    { label: "Low", value: 30, desc: "Users highly unlikely to attend are not allowed to register" },
    { label: "High", value: 70, desc: "Only users highly likely to attend are allowed to register" },
] as const


export default EditEventPage;
