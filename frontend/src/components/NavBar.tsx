import { useNavigate } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import Box from '@mui/material/Box';
import Avatar from '@mui/material/Avatar';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Tooltip from '@mui/material/Tooltip';
import PersonAdd from '@mui/icons-material/PersonAdd';
import Settings from '@mui/icons-material/Settings';
import Logout from '@mui/icons-material/Logout';
import React from "react";


function NavBar() {
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [userType, setUserType] = useState("");
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
  };

  const scrollToElement = (scrollTarget: string) => {
    const targetElement = document.getElementById(scrollTarget);

    if (targetElement) {
      targetElement.scrollIntoView({ behavior: "smooth" });
    }
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
    const userType = localStorage.getItem("userType") || "";
      setUserType(userType);
      console.log(userType);
    // if unauthorized, redirect to login page
    if (userType === "") {
      navigate("/unauthorized");
    }
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);




  return (
    <div
      className={`top-0 flex justify-between items-center fixed w-full ${isScrolled ? "bg-white shadow-sm" : ""
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

      <div className="mr-5">
        <Box sx={{ display: 'flex', alignItems: 'center', textAlign: 'center' }}>
          {/* displays userType for testing purposes */}
          <Typography sx={{ minWidth: 100 }}>{userType}</Typography>
          <Tooltip title="Account settings">
            <IconButton
              onClick={handleClick}
              size="small"
              sx={{ ml: 1 }}
              aria-controls={open ? 'account-menu' : undefined}
              aria-haspopup="true"
              aria-expanded={open ? 'true' : undefined}
            >
              <Avatar sx={{ width: 42, height: 42 }} />
            </IconButton>
          </Tooltip>
        </Box>
        <Menu
          anchorEl={anchorEl}
          id="account-menu"
          open={open}
          onClose={handleClose}
          onClick={handleClose}
          transformOrigin={{ horizontal: 'right', vertical: 'top' }}
          anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
        >
          <MenuItem onClick={() => handleClick1("/profilepage")}>
            <Avatar /> Edit Profile
          </MenuItem>
          {(userType === 'ORGANISER' || userType==='ADMIN' )&&
            <MenuItem onClick={() => handleClick1(`/eventform`)}>
              <ListItemIcon>
                <Avatar />
              </ListItemIcon>
              Create Event
            </MenuItem>}
          <Divider />
          {(userType === 'STUDENT' || userType==='ADMIN') &&
            <MenuItem onClick={() => handleClick1("/schedule")}>
              <ListItemIcon>
                <PersonAdd fontSize="small" />
              </ListItemIcon>
              Schedule
            </MenuItem>
          }
          <MenuItem onClick={handleClose}>
            <ListItemIcon>
              <Settings fontSize="small" />
            </ListItemIcon>
            Settings
          </MenuItem>
          <MenuItem onClick={handleLogout}>
            <ListItemIcon>
              <Logout fontSize="small" />
            </ListItemIcon>
            Logout
          </MenuItem>
        </Menu>
      </div>
    </div >
  );

}

export default NavBar;
function onEffect(arg0: () => void, arg1: any[]) {
  throw new Error("Function not implemented.");
}

