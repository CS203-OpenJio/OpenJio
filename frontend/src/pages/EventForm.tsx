import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import EventFormNavBar from "../components/EventForm/EventFormNavBar";
import { useState } from "react";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import Button from "@mui/material/Button";
import { styled } from "@mui/material/styles";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import axios from "axios";

function DateTimeBox() {
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DateTimePicker label="Basic date time picker" />
    </LocalizationProvider>
  );
}

function MapBox() {
  return (
    <iframe
      src="https://storage.googleapis.com/maps-solutions-dzz97assbk/address-selection/9p2v/address-selection.html"
      width="100%"
      height="350%"
      loading="lazy"
      className="border-0"
    />
  );
}

const VisuallyHiddenInput = styled("input")({
  clip: "rect(0 0 0 0)",
  clipPath: "inset(50%)",
  height: 1,
  overflow: "hidden",
  position: "absolute",
  bottom: 0,
  left: 0,
  whiteSpace: "nowrap",
  width: 1,
});

export default function EventForm() {
  const [preview, setPreview] = useState(false);
  const userName = "admin@admin.com";
  const password = "admin";

  async function handleClick() {
    const body = {
      name: `.Hack Certification Programme (DHCP)`,
      startDate: "2023-03-17",
      endDate: "2023-03-17",
      description:
        "Thinking of picking up a new tech-related skill? Or looking for something that will boost your portfolio? Look no further, .Hack’s much-anticipated DHCP is back this year! By participating in the DHCP, you will be able to take the coveted AWS’s Cloud Practitioner Certification, under the guidance of our experienced .Hack mentors!",
      capacity: 90,
      eventType: "Workshop",
      venue: "SCIS2 Computing Lab B1-1",
      image: "/dhcpworkshop.jpg",
      registered: false,
      visible: false,
    };
    await axios
      .post("http://localhost:8080/api/v1/events", body, {
        headers: {
          Authorization: "Basic " + btoa(`${userName}:${password}`),
        },
      })
      .catch((err) => {
        console.log(err.message);
      });
    window.location.reload();
  }

  return (
    <div className="">
      <EventFormNavBar />
      <div className="h-20"></div>
      <div className="flex flex-row m-2 justify-center">
        <div
          className={`${
            preview ? "max-w-[45vw]" : "max-w-[70vw]"
          } flex flex-col gap-10`}
        >
          <Grid container spacing={3}>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                id="firstName"
                name="firstName"
                label="First name"
                fullWidth
                autoComplete="given-name"
                variant="standard"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                id="lastName"
                name="lastName"
                label="Last name"
                fullWidth
                autoComplete="family-name"
                variant="standard"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                id="address1"
                name="address1"
                label="Address line 1"
                fullWidth
                autoComplete="shipping address-line1"
                variant="standard"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                id="address2"
                name="address2"
                label="Address line 2"
                fullWidth
                autoComplete="shipping address-line2"
                variant="standard"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                id="city"
                name="city"
                label="City"
                fullWidth
                autoComplete="shipping address-level2"
                variant="standard"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                id="state"
                name="state"
                label="State/Province/Region"
                fullWidth
                variant="standard"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                id="zip"
                name="zip"
                label="Zip / Postal code"
                fullWidth
                autoComplete="shipping postal-code"
                variant="standard"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                id="country"
                name="country"
                label="Country"
                fullWidth
                autoComplete="shipping country"
                variant="standard"
              />
            </Grid>
          </Grid>
          <DateTimeBox />
          <div className="font-roboto-serif">
            <Button
              component="label"
              variant="contained"
              startIcon={<CloudUploadIcon />}
              className="w-1/3"
            >
              Upload Image
              <VisuallyHiddenInput type="file" />
            </Button>
          </div>
          <div>
            <MapBox />
          </div>
          <button
            className="m-auto items-center cursor-pointer bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow"
            onClick={handleClick}
          >
            Add Event
          </button>
        </div>
      </div>
    </div>
  );
}
