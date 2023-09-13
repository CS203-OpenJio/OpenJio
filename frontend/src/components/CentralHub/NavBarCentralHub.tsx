function NavBarCentralHub() {
  return (
    <div className="flex justify-between items-center fixed w-full bg-white z-50 pt-2 pb-2">
      <img
        className="object-cover h-20 ml-2"
        alt="OpenJio Logo"
        src="/logo.png"
      />
      <div className="flex items-center font-medium text-center text-4xl font-ibm-plex-mono w-full flex-row justify-center gap-32">
        <div>Events</div>
        {/*drop down to Create an Event, View Created Events*/}
        <div>Schedule</div>
        <div>Profile</div>
      </div>
      <img
        className="object-cover mr-3"
        alt="Logout button"
        src="/logout.png"
      />
    </div>
  );
}

export default NavBarCentralHub;
