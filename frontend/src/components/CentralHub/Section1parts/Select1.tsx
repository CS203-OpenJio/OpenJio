import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../ui/select";

const Select1 = () => {
  return (
    <Select>
      <SelectTrigger className="w-[180px] font-ibm-plex-mono bg-white">
        <SelectValue placeholder="Event Type" />
      </SelectTrigger>
      <SelectContent className="font-ibm-plex-mono">
        <SelectItem value="Orientation Camp">Orientation Camp</SelectItem>
        <SelectItem value="Workshop">Workshop</SelectItem>
        <SelectItem value="Welfare Drive">Welfare Drive</SelectItem>
      </SelectContent>
    </Select>
  );
};

export default Select1;
