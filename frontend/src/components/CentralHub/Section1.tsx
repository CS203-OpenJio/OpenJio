import Select1 from "./Section1parts/Select1";
import SearchBar from "./Section1parts/SearchBar";
import Select2 from "./Section1parts/Select2";
import Select3 from "./Section1parts/Select3";

const Section1 = () => {
  return (
    <>
      <div className="mt-[80px] ml-14 mr-14 flex flex-row justify-between">
        <SearchBar />
        <Select1 />
        <Select2 />
        <Select3 />
      </div>
    </>
  );
};

export default Section1;
