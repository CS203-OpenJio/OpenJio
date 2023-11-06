import { FunctionComponent, useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import NavBarLite from "../components/HomeScreen/NavBarLite";
import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from "src/components/ui/alert-dialog";
import { handleSignUp } from "../utils/AuthController";
import "react-toastify/dist/ReactToastify.css";
import { toast } from "react-toastify";

const SignUpPage: FunctionComponent = () => {
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    const handleSuccess = (input: string) => {
        navigate(input);
    };

    const [fullname, setFullname] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [phonenumber, setPhonenumber] = useState("");

    const [fullnameError, setFullnameError] = useState("");
    const [emailError, setEmailError] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [phonenumberError, setPhonenumberError] = useState("");
    const [open, setOpen] = useState(false);

    const EMAIL_TOAST_ID = "email_validation_toast";
    const PHONE_TOAST_ID = "phone_validation_toast";
    const MATRICULATION_TOAST_ID = "matriculation_validation_toast";
    const NAME_TOAST_ID = "name_validation_toast";
    const PASSWORD_TOAST_ID = "password_validation_toast";

    const validatePassword = (pwd: string) => {
        if (pwd.length < 8 || pwd.length > 20) {
            if (!toast.isActive(PASSWORD_TOAST_ID)) {
                toast.error(
                    "Password must be between 8 and 20 characters long.",
                    { toastId: PASSWORD_TOAST_ID }
                );
            }
            return false;
        } else {
            toast.dismiss(PASSWORD_TOAST_ID);
        }
        return true;
    };

    const validatePhoneNumber = (phoneNumber: string) => {
        const pattern = /^\d{8}$/;
        if (!pattern.test(phoneNumber)) {
            if (!toast.isActive(PHONE_TOAST_ID)) {
                toast.error("Phone number must be 8 integers only.", {
                    toastId: PHONE_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(PHONE_TOAST_ID);
        }
        return true;
    };

    const validateMatriculationId = (matriculationId: string) => {
        const pattern = /^\d{8}$/;
        if (!pattern.test(matriculationId)) {
            if (!toast.isActive(MATRICULATION_TOAST_ID)) {
                toast.error("Matriculation ID must be 8 integers only.", {
                    toastId: MATRICULATION_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(MATRICULATION_TOAST_ID);
        }
        return true;
    };

    const validateName = (name: string) => {
        if (name.length < 5 || name.length > 15) {
            if (!toast.isActive(NAME_TOAST_ID)) {
                toast.error("Name must be between 5 and 15 characters long.", {
                    toastId: NAME_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(NAME_TOAST_ID);
        }
        return true;
    };

    const validateEmail = (email: string) => {
        const pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (!pattern.test(email)) {
            if (!toast.isActive(EMAIL_TOAST_ID)) {
                toast.error("Invalid email format.", {
                    toastId: EMAIL_TOAST_ID,
                });
            }
            return false;
        } else {
            toast.dismiss(EMAIL_TOAST_ID);
        }
        return true;
    };

    const handleSubmit = async () => {
        //sign up logic w backend api
        // Use the selectedYear state here along with other state variables
        // to send the data to the backend or perform other sign-up logic.

        if (
            !validatePassword(password) ||
            !validateEmail(email) ||
            !validateName(fullname) ||
            !validatePhoneNumber(phonenumber)
        ) {
            return; // Don't proceed if validation fails
        }

        try {
            setLoading(true);
            await handleSignUp(
                {
                    name: fullname,
                    email: email,
                    password: password,
                    phone: phonenumber,
                    userType: "O",
                }
            );
            toast.success("Organiser Sign up successful!");
            handleSuccess("/login");
            // Handle the successful login response here
        } catch (err: any) {
            console.log(err);
            setOpen(true);
            if (err.response?.data.message) {
                setErrorMessage(err.response.data.message);
            } else {
                setErrorMessage("An expected error occured!");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex">
            <NavBarLite />
            <div className="flex flex-row items-center justify-between w-full px-0">
                <div className="flex flex-col items-center bg-floralwhite w-full h-screen p-4 text-2xl text-black font-ibm-plex-mono">
                    {/* Sub Container */} {/* Split into Two Columns */}
                    {/* left Column Container */}{" "}
                    <div className="flex flex-col items-center w-[50%] mt-[60px]">
                        <div className="flex flex-col items-center bg-white rounded-11xl shadow-[0px_4px_4px_rgba(0,0,0,0.25)] border-[1px] border-solid border-gray-200 p-[24px] w-full space-y-4">
                            <b className="text-[40px]">
                                Organiser Sign up
                            </b>
                            <div
                                className="flex flex-col items-start w-full px-4 space-y-4 mb-4"
                                style={{ overflowY: "auto" }}
                            >
                                {" "}
                                {/* Adjusted to flex column, added spacing, and overflow hidden */}
                                <div className="flex flex-col items-start w-[100%] space-y-4 mb-4 overflow-hidden">
                                    <div className="flex flex-col w-full px-3 space-y-4">
                                        <div className="text-left font-medium leading-[20px]">
                                            Full Name
                                        </div>

                                        <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                                            <input
                                                placeholder="Enter your full name"
                                                className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                                                type="text"
                                                value={fullname}
                                                onChange={(e) => {
                                                    setFullname(e.target.value);
                                                    validateName(
                                                        e.target.value
                                                    );
                                                }}
                                            />
                                        </div>
                                    </div>
                                    <div className="flex flex-col w-full px-3 space-y-4">
                                        <div className="text-left font-medium leading-[20px]">
                                            School Email Address
                                        </div>
                                        <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                                            <input
                                                placeholder="Enter your email address"
                                                className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                                                type="text"
                                                value={email}
                                                onChange={(e) => {
                                                    setEmail(e.target.value);
                                                    validateEmail(
                                                        e.target.value
                                                    );
                                                }}
                                            />
                                        </div>
                                    </div>
                                    {/* Password */}
                                    <div className="flex flex-col w-full px-3 space-y-4">
                                        <div className="text-left font-medium leading-[20px]">
                                            Password
                                        </div>
                                        <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                                            <input
                                                placeholder="Enter your password"
                                                className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                                                type="password"
                                                value={password}
                                                onChange={(e) => {
                                                    setPassword(e.target.value);
                                                    validatePassword(
                                                        e.target.value
                                                    );
                                                }}
                                            />
                                        </div>
                                    </div>
                                    {/* Adjusted the container to flex column and added spacing between children elements */}
                                    {/* Phone Number */}
                                    <div className="flex flex-col w-full px-3 space-y-4">
                                        <div className="text-left font-medium leading-[20px]">
                                            Phone Number
                                        </div>
                                        <div className="w-[90%] rounded-md flex items-center px-3 border-[1px] border-solid border-black">
                                            <input
                                                placeholder="Enter your phone number"
                                                className="flex-1 bg-transparent border-none outline-none text-sm font-ibm-plex-mono"
                                                type="text"
                                                onChange={(e) => {
                                                    setPhonenumber(
                                                        e.target.value
                                                    );
                                                    validatePhoneNumber(
                                                        e.target.value
                                                    );
                                                }}
                                            />
                                        </div>
                                    </div>{" "}
                                </div>
                                <div className="flex flex-col items-stretch w-full">
                                    <button
                                        onClick={handleSubmit}
                                        className="flex items-center justify-center w-[360px] overflow-hidden text-sm bg-floralwhite rounded-xl border-[0.5px] border-solid border-black transform active:scale-95 cursor-pointer mx-auto"
                                    >
                                        <div className="flex items-center justify-center flex-1 py-2.5 px-3">
                                            <div className="font-medium">
                                                Sign Up
                                            </div>
                                        </div>
                                    </button>
                                </div>
                                <AlertDialog open={open}>
                                    <AlertDialogContent className="font-ibm-plex-mono">
                                        <AlertDialogHeader>
                                            <AlertDialogTitle>
                                                You have an error
                                            </AlertDialogTitle>
                                            <AlertDialogDescription>
                                                {errorMessage}
                                            </AlertDialogDescription>
                                        </AlertDialogHeader>
                                        <AlertDialogFooter>
                                            <AlertDialogCancel
                                                onClick={() => {
                                                    setOpen(false);
                                                    setErrorMessage("");
                                                }}
                                                className="hover:cursor-pointer font-ibm-plex-mono"
                                            >
                                                Close
                                            </AlertDialogCancel>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialog>
                                <div className="flex flex-col w-[337px] space-y-4 items-start text-mini">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SignUpPage;
