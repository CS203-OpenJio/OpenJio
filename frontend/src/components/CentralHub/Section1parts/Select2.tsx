import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../ui/select";

const Select2 = () => {
  return (
    <Select>
      <SelectTrigger className="w-[180px] font-ibm-plex-mono bg-white">
        <SelectValue placeholder="Category 2" />
      </SelectTrigger>
      <SelectContent className="font-ibm-plex-mono">
        <SelectItem value="Free">Free</SelectItem>
        <SelectItem value="Leisure">Leisure</SelectItem>
        <SelectItem value="Academic">Academic</SelectItem>
        <SelectItem value="Tech">SCIS</SelectItem>
        <SelectItem value="Business">Business</SelectItem>
        <SelectItem value="NA">NA</SelectItem>
      </SelectContent>
    </Select>
  );
};

export default Select2;
