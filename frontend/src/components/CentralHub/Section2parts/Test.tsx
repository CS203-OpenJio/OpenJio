const Test = () => {
  return (
    <div className="w-16 h-16 relative">
      <div className="absolute top-0 left-0 w-8 h-8 bg-red-500 rounded-full transform rotate-45"></div>
      <div className="absolute top-0 right-0 w-8 h-8 bg-red-500 rounded-full transform -rotate-45"></div>
    </div>
  );
};

export default Test;
