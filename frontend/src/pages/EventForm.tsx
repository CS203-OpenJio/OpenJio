import EventFormNavBar from "../components/EventForm/EventFormNavBar";
import { useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"
import { Input } from "../components/ui/input"
import { Button } from "../components/ui/button"
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../components/ui/form"
import { addDays, format } from "date-fns"
import { Calendar as CalendarIcon } from "lucide-react"
import { cn } from "../lib/utils"
import { Calendar } from "../components/ui/calendar"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "../components/ui/popover"
import React from "react";
import { DateRange } from "react-day-picker"



export default function EventForm() {
  const [preview, setPreview] = useState(false);

  return (
    <div>
      <EventFormNavBar />
      <div className="h-20"></div>
      <center>
        <div className="w-[80vw]">
          <h3 className="text-left font-ibm-plex-mono border-solid border-b-[1px] border-black leading-[0.1em] " >
            <span className="bg-[#FBF6EE] p-4 ml-28">Create an event</span>
          </h3>
          <p className="text-left font-ibm-plex-mono ml-32 text-[#484848] mt-[-5px]">Share your event with others!</p>
        </div>
      </center>

      <div className="flex flex-row justify-center">
        <div className="flex flex-column h-[82vh] items-center overflow-hidden">
          <div className="w-60 h-64 relative font-ibm-plex-mono overflow-hidden">
            <svg width="75" height="195" viewBox="0 0 75 195" fill="none" xmlns="http://www.w3.org/2000/svg" className="left-10 top-9 absolute">
              <g filter="url(#filter0_d_408_3807)">
                <path d="M48.1957 1.126C65.7067 33.9347 91.4984 50.2887 37.219 92.2674C-3.46438 127.906 -3.35016 148.446 25.9548 185.791" stroke="black" />
              </g>
              <defs>
                <filter id="filter0_d_408_3807" x="0.673828" y="0.890564" width="73.731" height="193.21" filterUnits="userSpaceOnUse" colorInterpolationFilters="sRGB">
                  <feFlood floodOpacity="0" result="BackgroundImageFix" />
                  <feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" result="hardAlpha" />
                  <feOffset dy="4" />
                  <feGaussianBlur stdDeviation="2" />
                  <feComposite in2="hardAlpha" operator="out" />
                  <feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0" />
                  <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_408_3807" />
                  <feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow_408_3807" result="shape" />
                </filter>
              </defs>
            </svg>

            <div className="w-44 h-6 left-0 top-0 absolute">
              <div className="w-6 h-6 left-0 top-0 absolute">
                <div className="w-6 h-6 left-0 top-0 absolute bg-white rounded-full border-solid border-black border-2" />
                <div className="w-2 h-5 left-[9.75px] top-[4.5px] absolute text-black text-sm font-medium leading-tight">1</div>
              </div>
              <div className="left-[32px] top-1 absolute text-black text-sm font-medium font-['IBM Plex Mono'] leading-tight">Event Details</div>
            </div>
            <div className="w-48 h-6 left-0 top-[228px] absolute">
              <div className="w-6 h-6 left-0 top-0 absolute">
                <div className="w-6 h-6 left-0 top-0 absolute bg-white rounded-full border-solid border-black border-2" />
                <div className="w-2 h-5 left-[9.75px] top-[4.5px] absolute text-black text-sm font-medium leading-tight">3</div>
              </div>
              <div className="left-[32px] top-1 absolute text-black text-sm font-medium leading-tight">Attendee Options</div>
            </div>
            <div className="w-40 h-6 left-[92px] top-[118px] absolute">
              <div className="w-6 h-6 left-0 top-0 absolute">
                <div className="w-6 h-6 left-0 top-0 absolute bg-white rounded-full border-solid border-black border-2" />
                <div className="w-2 h-5 left-[9.75px] top-[4.5px] absolute text-black text-sm font-medium leading-tight">2</div>
              </div>
              <div className="left-[32px] top-1 absolute text-black text-sm font-medium font-['IBM Plex Mono'] leading-tight">Event Display</div>
            </div>
          </div>
        </div>
        <div className="p-10">
          <InputForm />
        </div>

      </div>
    </div>
  );

}

const FormSchema = z.object({
  username: z.string().min(2, {
    message: "Username must be at least 2 characters.",
  }),
  capacity: z.string().refine((value) => {
    // Ensure it's a numeric string
    const numericValue = Number(value);
    if (isNaN(numericValue)) {
      return false; // Not a number
    }
    // Limit to a maximum of 1000
    return numericValue >= 1 && numericValue <= 1000;
  }, {
    message: "Capacity must be a number between 1 and 1000.",
  }),
  location: z.string().min(4, {
    message: "Location must be at least 4 characters.",
  }),
  date: z.custom((value) => {
    // Perform your custom validation for DateRange here
    // You can check if value is a valid DateRange or not
    // Return true if it's valid, or false if it's not
    return value != null;
  }, {
    message: "Please set a date range.", // Customize the error message as needed
  }),
})

function InputForm() {
  const [date, setDate] = React.useState<DateRange | undefined>({
    from: new Date(2023, 0, 20, 0, 0, 0, 0), // Year, Month, Day, Hours, Minutes, Seconds, Milliseconds
    to: addDays(new Date(2023, 0, 20, 0, 0, 0, 0), 20),
  });
  
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
  })

  function onSubmit(data: z.infer<typeof FormSchema>) {
    toast(JSON.stringify(data, null, 2));
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
        <FormField
          control={form.control}
          name="username"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="font-ibm-plex-mono">Event Name</FormLabel>
              <FormControl>
                <Input placeholder="Event Name" {...field} />
              </FormControl>
              <FormDescription className="font-ibm-plex-mono">
                This is your event’s name, you can edit this in the future
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
                <Input placeholder="Event Capacity" {...field} />
              </FormControl>
              <FormDescription className="font-ibm-plex-mono">
                This is your event’s capacity, you can edit this in the future
              </FormDescription>
              <FormMessage className="font-ibm-plex-mono" />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="location"
          render={({ field }) => (
            <FormItem>
              <FormLabel className="font-ibm-plex-mono">Event Location</FormLabel>
              <FormControl>
                <Input placeholder="Event Location" {...field} />
              </FormControl>
              <FormDescription className="font-ibm-plex-mono">
                This is your event’s location, you can edit this in the future
              </FormDescription>
              <FormMessage className="font-ibm-plex-mono" />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="date"
          render={({ field }) => (
            <FormItem className="flex flex-col">
              <FormLabel className="font-ibm-plex-mono">Event Duration</FormLabel>
              <div className={cn("grid gap-2")}>
                <Popover>
                  <PopoverTrigger asChild>
                    <Button
                      id="date"
                      variant={"outline"}
                      className={cn(
                        "w-[300px] justify-start text-left font-normal hover:cursor-pointer",
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
                This is your event’s duration, you can edit this in the future
              </FormDescription>
              <FormMessage className="font-ibm-plex-mono" />
            </FormItem>
          )}
        />
        <Button type="submit" className="hover:cursor-pointer">Next</Button>
      </form>
      <ToastContainer />
    </Form>
  )
}