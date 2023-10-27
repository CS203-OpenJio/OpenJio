import { useState, useEffect } from "react";
import { getEvents } from "../utils/CreatedEventController";
import NavBar from "../components/NavBar";
import { useNavigate } from "react-router-dom";
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "src/components/ui/table";

const CreatedEvents = () => {

    const [events, setEvents] = useState<any[]>([]);
    const navigate = useNavigate();

    const handleClick = (path: string) => {
        navigate(path);
    };

    useEffect(() => {
        const fetchEvents = async () => {
            const events = await getEvents();
            console.log(events);
            setEvents(events);
            console.log(events.length)
        };
        if (localStorage.getItem("userType") === "STUDENT") {
            navigate("/unauthorized");
        }
        fetchEvents();
    }, []);

    return (
        <div className="h-[95%] font-ibm-plex-mono">
            <NavBar />
            <div className="container mx-auto">
                <h1 className="text-3xl font-bold mb-8 mt-[80px]">Created Events</h1>
                {events.length === 0 ? (
                    <p>You haven't created any events yet.</p>
                ) : (
                    <div className=" text-4xl">
                        <Table>
                            <TableHeader>
                                <TableRow>
                                    <TableHead>Event Id</TableHead>
                                    <TableHead>Event Name</TableHead>
                                    <TableHead>Venue</TableHead>
                                    <TableHead>Date from</TableHead>
                                    <TableHead>Date to</TableHead>
                                    <TableHead>Status</TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {events.map((events: any) => (
                                    <TableRow key={events.name}>
                                        <TableCell className="font-medium">
                                            {events.id}
                                        </TableCell>
                                        <TableCell>{events.name}</TableCell>
                                        <TableCell>{events.venue}</TableCell>
                                        <TableCell>
                                            {new Date(events.startDateTime).toLocaleDateString()}
                                        </TableCell>
                                        <TableCell>
                                            {new Date(events.endDateTime).toLocaleDateString()}
                                        </TableCell>
                                        <TableCell>
                                            {events.visible ? "Visible" : "Not Visible"}
                                        </TableCell>
                                        <TableCell>
                                            <div className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-2 rounded w-10 text-center hover:cursor-pointer"
                                                onClick={() => handleClick("/editeventpage?id=" + events.id)}>
                                                Edit
                                            </div>
                                        </TableCell>
                                        <TableCell className="w-[15%]">
                                            <div className="bg-green-600 hover:bg-blue-700 text-white font-bold py-2 px-2 rounded text-center hover:cursor-pointer"
                                                onClick={() => handleClick("/eventparticipant?id=" + events.id)}>
                                                View Participants
                                            </div>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </div>
                )}
            </div>
        </div>

    );
};

export default CreatedEvents;
