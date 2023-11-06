import Section2 from "../components/CentralHub/Section2";
import NavBar from "../components/NavBar";
import SearchBar from "../components/CentralHub/Section1parts/SearchBar";
import Section3 from "../components/CentralHub/Section3";

function CentralHub() {
  return (
    <div>
      <NavBar />
      <div className="flex flex-row justify-between ml-30 ml-14 mr-14 mb-2 mt-[100px]">
        <div className="pb-3 ml-14 font-source-serif-pro text-darkslateblue text-31xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)">
          Upcoming Events
        </div>
        <SearchBar />

      </div>
      <Section2 />

      <div className="flex flex-row justify-between ml-30 ml-14 mr-14 mb-2 mt-[80px]">
        <div
          id="All Events"
          className="pb-3 ml-14 font-source-serif-pro text-darkslateblue text-31xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)"
        >
          All Events
        </div>
        <SearchBar />

      </div>
      <Section3 />
    </div>
  );
}

export default CentralHub;
