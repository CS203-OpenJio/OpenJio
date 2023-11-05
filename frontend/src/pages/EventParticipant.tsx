import React, {
  useState,
  useMemo,
  useCallback,
  useRef,
  useEffect,
} from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "src/components/ui/table";
import {
  Input,
  Button,
  ToggleButtonGroup,
  ToggleButton,
  ButtonGroup,
} from "@mui/material";
import { CSVLink } from "react-csv";
import NavBar from "../components/NavBar";
import {
  getParticipants,
  getStatusParticipants,
  allocateSlots,
  closeEvent,
} from "../utils/CreatedEventController";
import { getEvents } from "../utils/EditEventController";
import { useNavigate, useSearchParams } from "react-router-dom";
import { toast } from "react-toastify";
import CloudDownloadIcon from "@mui/icons-material/CloudDownload";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

interface Participant {
  id: number;
  name: string;
  email: string;
  phone: string;
  matricNo: string;
}

const participantsData: Participant[] = [
  {
    id: 1,
    name: "no one found",
    email: "no one found",
    phone: "no one found",
    matricNo: "no one found",
  },
];

const EventParticipant = () => {
  const [searchParams] = useSearchParams();
  const eventId = searchParams.get("id");
  const [participants, setParticipants] = useState(participantsData);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortConfig, setSortConfig] = useState<{
    key: string;
    direction: string;
  }>({ key: "", direction: "" });
  const [alignment, setAlignment] = React.useState("web");
  const [completed, setCompleted] = useState(false);
  const [eventName, setEventName] = useState("");

  const handleChange = async (
    event: React.MouseEvent<HTMLElement>,
    newAlignment: string
  ) => {
    setAlignment(newAlignment);
    if (eventId) {
      if (newAlignment === "ALL") {
        await getParticipants(eventId ?? "").then((eventParticipants) => {
          setParticipants(eventParticipants);
        });
      } else if (
        newAlignment === "ACCEPTED" ||
        newAlignment === "PENDING" ||
        newAlignment === "REJECTED"
      ) {
        await getStatusParticipants(eventId, newAlignment).then(
          (eventParticipants) => {
            setParticipants(eventParticipants);
          }
        );
      } else {
        setAlignment("ALL");
        await getParticipants(eventId ?? "").then((eventParticipants) => {
          setParticipants(eventParticipants);
        });
      }
    }
  };

  const handleSearch = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setSearchTerm(event.target.value);
    },
    []
  );

  const handleSort = useCallback(
    (key: string) => {
      let direction = "ascending";
      if (sortConfig.key === key && sortConfig.direction === "ascending") {
        direction = "descending";
      }
      setSortConfig({ key, direction });
    },
    [sortConfig]
  );

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
  };

  useEffect(() => {
    if (eventId) {
      updateCompleted();
      getParticipants(eventId).then((eventParticipants) => {
        setParticipants(eventParticipants);
        handleChange({} as React.MouseEvent<HTMLElement>, "ALL");
      });
    } else {
      toast.error("Event not found.");
    }
  }, []);

  const handleAllocate = async () => {
    if (eventId) {
      try {
        await allocateSlots(eventId);
        await getStatusParticipants(eventId, "ACCEPTED").then(
          (eventParticipants) => {
            setParticipants(eventParticipants);
            handleChange({} as React.MouseEvent<HTMLElement>, "ACCEPTED");
          }
        );
      } catch (error: any) {
        console.log(error);
        toast.error(error.message);
      }
    }
  };

  const handleExport = useCallback(() => {
    const headers = [
      { label: "ID", key: "id" },
      { label: "Name", key: "name" },
      { label: "Email", key: "email" },
      { label: "Phone", key: "phone" },
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
    if (sortConfig.key !== "") {
      sortableParticipants.sort((a, b) => {
        const key = sortConfig.key as keyof Participant;
        if (a[key] < b[key]) {
          return sortConfig.direction === "ascending" ? -1 : 1;
        }
        if (a[key] > b[key]) {
          return sortConfig.direction === "ascending" ? 1 : -1;
        }
        return 0;
      });
    }
    return sortableParticipants;
  }, [participants, sortConfig]);

  const filteredParticipants = useMemo(() => {
    let filteredParticipants = [...sortedParticipants];
    if (searchTerm !== "") {
      filteredParticipants = filteredParticipants.filter((participant) =>
        participant.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
    return filteredParticipants;
  }, [searchTerm, sortedParticipants]);

  const [openConfirmationDialog, setOpenConfirmationDialog] = useState(false);

  const handleClose = () => {
    setOpenConfirmationDialog(true);
  };

  const handleCloseEvent = async () => {
    setOpenConfirmationDialog(false);
    if (eventId) {
      try {
        await closeEvent(eventId);
        toast.success("Event closed.");
        updateCompleted();
      } catch (error) {
        toast.error("Error closing event.");
      }
    } else {
      toast.error("Event not found.");
    }
  };

  return (
    <div>
      <NavBar />
      <div className="container mx-auto max-w-4xl mt-[80px]">
        <div className="py-8">
          <h2 className="text-2xl leading-tight font-ibm-plex-mono">
            Participants
            <span className="text-sm">
              {" "}
              of <span className="underline tracking-tight">{eventName}</span>
            </span>
          </h2>
          <div className="flex flex-row mb-1 sm:mb-0 justify-between h-8">
            <div className="flex-col">
              <ToggleButtonGroup
                className="h-8"
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
              <Input
                placeholder="Search"
                className="ml-2"
                value={searchTerm}
                onChange={handleSearch}
              />
            </div>

            <div className="flex">
              <ButtonGroup
                variant="contained"
                aria-label="outlined primary button group"
              >
                <Button className="bg-green-500" color="success">
                  <CSVLink
                    {...handleExport()}
                    filename={"participants.csv"}
                    className="text-white no-underline flex"
                  >
                    <CloudDownloadIcon />
                    <span className="ml-1">.CSV</span>
                  </CSVLink>
                </Button>
                <Button onClick={handleAllocate} disabled={completed}>
                  Allocate
                </Button>
                <Button
                  onClick={handleClose}
                  disabled={completed}
                  color="error"
                >
                  {completed ? "event closed" : "close event"}
                </Button>
              </ButtonGroup>
            </div>
          </div>

          <Dialog
    open={openConfirmationDialog}
    onClose={() => setOpenConfirmationDialog(false)}
    aria-labelledby="alert-dialog-title"
    aria-describedby="alert-dialog-description"
    sx={{ '& .MuiDialog-paper': { borderRadius: '16px' } }} // Apply rounded corners
>
            <DialogTitle id="alert-dialog-title">{"Close Event"}</DialogTitle>
            <DialogContent>
              <DialogContentText id="alert-dialog-description">
                Are you sure you want to close this event?
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button
                onClick={() => setOpenConfirmationDialog(false)}
                color="primary"
              >
                Cancel
              </Button>
              <Button onClick={handleCloseEvent} color="primary" autoFocus>
                Confirm
              </Button>
            </DialogActions>
          </Dialog>

          <div className="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto font-ibm-plex-mono">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead
                    className="hover:cursor-pointer"
                    onClick={() => handleSort("id")}
                  >
                    ID
                  </TableHead>
                  <TableHead
                    className="hover:cursor-pointer"
                    onClick={() => handleSort("name")}
                  >
                    Name
                  </TableHead>
                  <TableHead
                    className="hover:cursor-pointer"
                    onClick={() => handleSort("email")}
                  >
                    Email
                  </TableHead>
                  <TableHead
                    className="hover:cursor-pointer"
                    onClick={() => handleSort("phone")}
                  >
                    Phone
                  </TableHead>
                  <TableHead
                    className="hover:cursor-pointer"
                    onClick={() => handleSort("phone")}
                  >
                    Matric No.
                  </TableHead>
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
