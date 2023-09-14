import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../ui/select";

const DropDownSelect = () => {
  return (
    <Select>
      <SelectTrigger className="w-[180px] font-ibm-plex-mono">
        <SelectValue placeholder="Theme" />
      </SelectTrigger>
      <SelectContent className="font-ibm-plex-mono">
        <SelectItem value="light">Light</SelectItem>
        <SelectItem value="dark">Dark</SelectItem>
        <SelectItem value="system">System</SelectItem>
      </SelectContent>
    </Select>
  );
};

export default DropDownSelect;
