import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import Box from '@mui/material/Box';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Tooltip from '@mui/material/Tooltip';
import CloseIcon from '@mui/icons-material/Close';
import HomeIcon from '@mui/icons-material/Home';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import GridViewIcon from '@mui/icons-material/GridView';
import React from "react";
import { Button, Divider, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText } from "@mui/material";

interface MenuItem {
  name: string;
  path: string;
  icon: JSX.Element;
  authorizedRoles: string[];
}

//Add here for more menu items!
const menuItems: MenuItem[] = [
  {
    name: "Home",
    path: "/centralhub",
    icon: <HomeIcon />,
    authorizedRoles: ["ADMIN", "STUDENT", "ORGANISER"],
  },
  {
    name: "Profile",
    path: "/profilepage",
    icon: <AccountCircleIcon />,
    authorizedRoles: ["ADMIN", "STUDENT"],
  },
  {
    name: "Create Event",
    path: "/eventform",
    icon: <AddCircleOutlineIcon />,
    authorizedRoles: ["ADMIN", "ORGANISER"],
  },
  {
    name: "Schedule",
    path: "/schedule",
    icon: <CalendarMonthIcon />,
    authorizedRoles: ["ADMIN", "STUDENT"],
  },
  {
    name: "Created Events",
    path: "/createdevents",
    icon: <GridViewIcon />,
    authorizedRoles: ["ADMIN", "ORGANISER"],
  },
];

function NavBar() {
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [userType, setUserType] = useState("");
  const [name, setName] = useState("loading...");
  const open = Boolean(anchorEl);

  // handleClick for logout
  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/logout");
  };

  // handleClick for other parts of NavBar
  const handleClick1 = (path: string) => {
    navigate(path);
  };

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setState({ ...state, ["right"]: false });
  };

  //For NavBar transparency effect
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 20) {
        setIsScrolled(true);
      } else {
        setIsScrolled(false);
      }
    };

    window.addEventListener("scroll", handleScroll);
    const userType = localStorage.getItem("userType") || "";
    const name = localStorage.getItem("name") || "";
    setUserType(userType);
    setName(name);
    // if unauthorized, redirect to login page
    if (userType === "" || name === "") {
      navigate("/unauthorized");
    }
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  const [state, setState] = React.useState({
    top: false,
    left: false,
    bottom: false,
    right: false,
  });

  const toggleDrawer = (anchor: "right", open: boolean) =>
    (event: React.KeyboardEvent | React.MouseEvent) => {
      if (
        event.type === 'keydown' &&
        ((event as React.KeyboardEvent).key === 'Tab' ||
          (event as React.KeyboardEvent).key === 'Shift')
      ) {
        return;
      }

      setState({ ...state, [anchor]: open });
    };

  const list = () => (
    <Box
      sx={{ width: 300 }}
      role="presentation"
      onKeyDown={toggleDrawer("right", false)}
    >
      <div className="flex p-4 flex-row items-center justify-between cursor-default">
        <div className="flex flex-row items-center">
          <Avatar className="w-10 h-10 mr-3" />
          <Typography>{name}</Typography>
        </div>
        <div className="hover:bg-slate-200 rounded-lg" onClick={handleClose}>
          <CloseIcon className="cursor-pointer p-1" />
        </div>
      </div>
      <Divider />
      <List>
        {menuItems.map((item) => (
          item.authorizedRoles.includes(userType) &&
          <ListItem key={item.name} onClick={() => navigate(item.path)} disablePadding>
            <ListItemButton>
              <ListItemIcon>
                {item.icon}
              </ListItemIcon>
              <ListItemText primary={item.name} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
      <Divider />
      <List>
        <ListItem key={"signout"} onClick={handleLogout} disablePadding>
          <ListItemButton>
            <ListItemText primary={"Sign Out"} />
          </ListItemButton>
        </ListItem>
      </List>
    </Box>
  );




  return (
    <div
      className={`top-0 flex justify-between items-center fixed w-full ${isScrolled ? "bg-[#FBF6EE] shadow-sm" : ""
        } z-50 pt-2 pb-2`}
      style={{
        transition: "background-color 0.2s ease-in-out",
      }}
    >
      <img
        onClick={() => handleClick1("/centralhub")}
        className="object-cover h-12 ml-5 cursor-pointer"
        alt="OpenJio Logo"
        src="/logo.png"
      />

      <div className="mr-5 rounded-xl bg-white shadow-md">
        <React.Fragment key={"right"}>
          <Button onClick={toggleDrawer("right", true)}>{
            <Box sx={{ display: 'flex', alignItems: 'center', textAlign: 'center' }}>
              {/* displays userType for testing purposes */}
              <Typography sx={{ minWidth: 100 }}>{name}</Typography>
              <Avatar className="w-10 h-10 ml-2" />
            </Box>}
          </Button>
          <Drawer
            anchor={"right"}
            open={state["right"]}
            onClose={toggleDrawer("right", false)}
          >
            {list()}
          </Drawer>
        </React.Fragment>

      </div>
    </div >
  );

}

export default NavBar;
function onEffect(arg0: () => void, arg1: any[]) {
  throw new Error("Function not implemented.");
}

