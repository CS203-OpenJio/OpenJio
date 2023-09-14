import DropDownSelect from "../components/CentralHub/DropDownSelect";
import NavBarCentralHub from "../components/CentralHub/NavBarCentralHub";
import SearchBar from "../components/CentralHub/SearchBar";

function CentralHub() {
  return (
    <div className="relative">
      <NavBarCentralHub />
      <div className="relative top-50 flex-col justify-normal">
        <DropDownSelect />
        <SearchBar />
      </div>
    </div>
  );
}

export default CentralHub;
