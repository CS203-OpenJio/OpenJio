import DropDownSelect from "../components/CentralHub/DropDownSelect";
import NavBarCentralHub from "../components/CentralHub/NavBarCentralHub";
import SearchBar from "../components/CentralHub/SearchBar";
import SearchBar1 from "../components/CentralHub/SearchBar1";

function CentralHub() {
  return (
    <div>
      <NavBarCentralHub />
      <div className="mt-40 flex-col justify-normal">
        <DropDownSelect />
        <SearchBar />
        <SearchBar1 />
      </div>
    </div>
  );
}

export default CentralHub;
