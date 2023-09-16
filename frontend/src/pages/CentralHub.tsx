import NavBarCentralHub from "../components/CentralHub/Section1parts/NavBarCentralHub";
import Section1 from "../components/CentralHub/Section1";
import Section2 from "../components/CentralHub/Section2";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";

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
    </div>
  );
}

export default CentralHub;
