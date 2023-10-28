import { useEffect, useState } from "react";
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
import { Check, ChevronsUpDown } from "lucide-react"
import { cn } from "../lib/utils"
import { Calendar } from "../components/ui/calendar"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "../components/ui/popover"
import React from "react";
import { DateRange } from "react-day-picker"
import NavBar from "../components/NavBar";
import { Textarea } from "../components/ui/textarea";
import {
  Command,
  CommandGroup,
  CommandItem,
} from "../components/ui/command"
import { Switch } from "../components/ui/switch"
import { Slider } from "../components/ui/slider";
import { createEvent } from "../utils/EventRegistrationController";
import { useNavigate } from "react-router-dom";



export default function EventForm() {

  // check if user is an organiser
  useEffect(() => {
    if (localStorage.getItem("userType") === "STUDENT") {
      navigate("/unauthorized");
    }
  }, []);

  const navigate = useNavigate();
  const [submitted, setSubmit] = useState(false);
  // shows the current step of the form
  const [currentStep, setStep] = useState(1);

  // form data
  const [name, setName] = useState("");
  const [capacity, setCapacity] = useState("");
  const [venue, setVenue] = useState("");
  const [date, setDate] = React.useState<DateRange | undefined>({
    from: new Date(),
    to: addDays(new Date(), 20),
  });
  const [desc, setDesc] = useState("");
  const [image, setImage] = useState(null);
  const [algo, setAlgo] = useState("");
  const [visible, setVisible] = useState(false);
  const [minScore, setMinScore] = useState(80);

  // default values for form
  const form1 = useForm<z.infer<typeof FormSchema1>>({
    resolver: zodResolver(FormSchema1),
    defaultValues: {
      name: name,
      capacity: capacity,
      venue: venue,
      date: date,
    },
  });
  const form2 = useForm<z.infer<typeof FormSchema2>>({
    resolver: zodResolver(FormSchema2),
    defaultValues: {
      description: desc,
      image: image,
    },
  });
  const form3 = useForm<z.infer<typeof FormSchema3>>({
    resolver: zodResolver(FormSchema3),
    defaultValues: {
      algo: algo,
      visible: visible,
      minScore: minScore,
    },
  });

  // function to go to next step
  function nextButton1(data: z.infer<typeof FormSchema1>) {
    if (data) {
      setName(data.name);
      setCapacity(data.capacity);
      setVenue(data.venue);
      setDate({ from: data.date.from, to: data.date.to });
      setStep(2);
    }
    else {
      toast("error");
    }
  }
  function nextButton2(data: z.infer<typeof FormSchema2>) {
    if (data) {
      setImage(data.image);
      setDesc(data.description);
      setStep(3);
    }
    else {
      toast("error");
    }
  }
  function nextButton3(data: z.infer<typeof FormSchema3>) {
    if (data) {
      setAlgo(data.algo);
      setVisible(data.visible);
      setMinScore(data.minScore);
    } else {
      toast("error");
    }
  }

  // use effect for form validation
  useEffect(() => {
    if (!submitted && name != "" && capacity != "" && venue != "" && date != undefined && desc != "") {
      setSubmit(false);
      submitData();
    }
  }, [algo, visible, minScore]);

  //submit data to backend
  async function submitData() {
    const formData = {
      name: name,
      capacity: capacity,
      venue: venue,
      date: date,
      description: desc,
      image: image,
      algo: algo,
      visible: visible,
      minScore: minScore,
    };

    // make API call
    try {
      await createEvent(formData);
      setSubmit(true);
      toast.success(`${name} created successfully!`);
      navigate("/centralhub");
    } catch (err) {
      toast((err as { message: string })?.message || "Unknown error");
    }
  }

  return (
    <div>
      <NavBar />
      <div className="h-20"></div>
      <center className="">
        <div className="w-[80vw]">
          <h3 className="text-left font-ibm-plex-mono border-solid border-b-[1px] border-black leading-[0.1em] " >
            <span className="bg-[#FBF6EE] p-[1vw] ml-[6vw]">Create an event</span>
          </h3>
          <p className="text-left font-ibm-plex-mono ml-[7vw] text-[#484848] mt-[-5px]">Share your event with others!</p>
        </div>
      </center>

      <div className="flex flex-row justify-center items-center w-[80vw] m-auto h-[70vh] gap-20">
        <div className="w-60 min-w-60 h-64 relative font-ibm-plex-mono overflow-hidden cursor-default">
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
              <div className={`w-6 h-6 left-0 top-0 absolute rounded-full border-solid border-black border-2 ${currentStep == 1 ? "bg-black hover:bg-slate-700" : "bg-white"}`} />
              <div className={`w-2 h-5 left-[9.75px] top-[4.5px] absolute text-sm font-medium leading-tight cursor-default ${currentStep == 1 ? "text-white" : "text-black"}`} >1</div>
            </div>
            <div className="left-[32px] top-1 absolute text-black text-sm font-medium font-['IBM Plex Mono'] leading-tight">Event Details</div>
          </div>
          <div className="w-48 h-6 left-0 top-[228px] absolute">
            <div className="w-6 h-6 left-0 top-0 absolute">
              <div className={`w-6 h-6 left-0 top-0 absolute rounded-full border-solid border-black border-2 ${currentStep == 3 ? "bg-black hover:bg-slate-700" : "bg-white"}`} />
              <div className={`w-2 h-5 left-[9.75px] top-[4.5px] absolute text-sm font-medium leading-tight cursor-default ${currentStep == 3 ? "text-white" : "text-black"}`}>3</div>
            </div>
            <div className="left-[32px] top-2 absolute text-black text-sm font-medium leading-tight">Attendee Options</div>
          </div>
          <div className="w-40 h-6 left-[92px] top-[118px] absolute">
            <div className="w-6 h-6 left-0 top-0 absolute">
              <div className={`w-6 h-6 left-0 top-0 absolute rounded-full border-solid border-black border-2 ${currentStep == 2 ? "bg-black hover:bg-slate-700" : "bg-white"}`} />
              <div className={`w-2 h-5 left-[9.75px] top-[4.5px] absolute text-sm font-medium leading-tight cursor-default ${currentStep == 2 ? "text-white" : "text-black"}`}>2</div>
            </div>
            <div className="left-[32px] top-1 absolute text-black text-sm font-medium font-['IBM Plex Mono'] leading-tight">Event Display</div>
          </div>
        </div>

        {currentStep == 1 &&
          <div className="grow">
            <Form {...form1}>
              <form onSubmit={form1.handleSubmit(nextButton1)} className="space-y-6">
                <FormField
                  control={form1.control}
                  name="name"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="font-ibm-plex-mono">Event Name</FormLabel>
                      <FormControl>
                        <Input className="bg-white" placeholder="Event Name" {...field} />
                      </FormControl>
                      <FormDescription className="font-ibm-plex-mono">
                        This is your event’s name, you can edit this in the future
                      </FormDescription>
                      <FormMessage className="font-ibm-plex-mono" />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form1.control}
                  name="capacity"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="font-ibm-plex-mono">Event Capacity</FormLabel>
                      <FormControl>
                        <Input className="bg-white" placeholder="Event Capacity" {...field} />
                      </FormControl>
                      <FormDescription className="font-ibm-plex-mono">
                        This is your event’s capacity, you can edit this in the future
                      </FormDescription>
                      <FormMessage className="font-ibm-plex-mono" />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form1.control}
                  name="venue"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel className="font-ibm-plex-mono">Event Venue</FormLabel>
                      <FormControl>
                        <Input className="bg-white" placeholder="Event Venue" {...field} />
                      </FormControl>
                      <FormDescription className="font-ibm-plex-mono">
                        This is your event’s location, you can edit this in the future
                      </FormDescription>
                      <FormMessage className="font-ibm-plex-mono" />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form1.control}
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
                                form1.setValue("date", newDate);  // update the form state
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
                <div className="flex flex-row justify-between w-[100%]">
                  <div></div>
                  <Button type="submit" className="hover:cursor-pointer">Next</Button>
                </div>

              </form>
            </Form>
          </div>}

        {currentStep == 2 && <div className="grow">
          <Form {...form2}>
            <form onSubmit={form2.handleSubmit(nextButton2)} className="space-y-6">
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
                      This is your event’s image, you can change or delete this in the future (optional)
                    </FormDescription>
                    <FormMessage className="font-ibm-plex-mono" />
                  </FormItem>
                )}
              />
              <FormField
                control={form2.control}
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
              <div className="flex flex-row justify-between w-[100%]">
                <Button onClick={() => { setStep(1) }} className="hover:cursor-pointer">Back</Button>
                <Button type="submit" className="hover:cursor-pointer">Next</Button>
              </div>

            </form>
          </Form>
        </div>}

        {currentStep == 3 && <div className="grow">
          <Form {...form3}>
            <form onSubmit={form3.handleSubmit(nextButton3)} className="space-y-6">
              <FormField
                control={form3.control}
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
                              <CommandItem
                                value={language.label}
                                className="font-ibm-plex-mono"
                                key={language.value}
                                onSelect={() => {
                                  form3.setValue("algo", language.value)
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
                            ))}
                          </CommandGroup>
                        </Command>
                      </PopoverContent>
                    </Popover>
                    <FormDescription>
                      This chooses the way your slots are distributed, you can change this in the future.
                    </FormDescription>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form3.control}
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
                      Let your event be visible immediately?
                    </FormDescription>

                  </FormItem>
                )}
              />
              <FormField
                control={form3.control}
                name="minScore"
                render={({ field }) => (
                  <FormItem className="flex flex-col rounded-lg font-ibm-plex-mono">
                    <FormLabel className="text-base">
                      Attendee Minimum Score
                    </FormLabel>
                    <FormControl>
                      <div className="flex flex-row">
                        <Slider max={100} step={1}
                          className="hover:cursor-pointer"
                          value={[field.value]}
                          onValueChange={(newValue) => {
                            field.onChange(newValue[0]); // Update the form field value
                          }} />
                        <div className="ml-6">
                          {[field.value]}
                        </div>
                      </div>
                    </FormControl>
                    <FormDescription>
                      Minimum event participation score for attendees signing up.
                    </FormDescription>

                  </FormItem>
                )}
              />

              <div className="flex flex-row justify-between w-[100%]">
                <Button onClick={() => { setStep(2) }} className="hover:cursor-pointer">Back</Button>
                <Button type="submit" className="hover:cursor-pointer">Submit</Button>
              </div>
            </form>
          </Form>
        </div>}
      </div>
      <ToastContainer />
    </div>
  );
}

const FormSchema1 = z.object({
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
})
const FormSchema2 = z.object({
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
})
const FormSchema3 = z.object({
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
  { label: "Normal Queue", value: "FCFS" },
  { label: "Random Selection", value: "Random" },
  { label: "Weighted Random Selection", value: "Weighted Random" },
  { label: "Weighted Random Selection", value: "Weighted Random" },
] as const


