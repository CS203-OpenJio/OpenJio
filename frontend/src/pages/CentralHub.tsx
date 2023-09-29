import Section1 from "../components/CentralHub/Section1";
import Section2 from "../components/CentralHub/Section2";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import Select1 from "../components/CentralHub/Section1parts/Select1";
import Select2 from "../components/CentralHub/Section1parts/Select2";
import Select3 from "../components/CentralHub/Section1parts/Select3";
import SearchBar from "../components/CentralHub/Section1parts/SearchBar";
import Section3 from "../components/CentralHub/Section3";

function CentralHub() {
  return (
    <div>
      <NavBarTest2 />
      <Section1 />
      <p></p>
      <div className="pb-3 ml-14 font-source-serif-pro text-darkslateblue text-31xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)">
        Popular & Trending
      </div>
      <Section2 />
      <p></p>
      <div
        id="All Events"
        className="pb-3 ml-14 font-source-serif-pro text-darkslateblue text-31xl [text-shadow:0px_4px_4px_rgba(0,_0,_0,_0.25)"
      >
        All Events
      </div>
      <div className="flex flex-row justify-between ml-30 ml-14 mr-14 mb-5">
        <SearchBar />
        <Select1 />
        <Select2 />
        <Select3 />
      </div>
      <Section3 />
    </div>
  );
}

export default CentralHub;
