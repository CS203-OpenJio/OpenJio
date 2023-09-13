import { FunctionComponent, useMemo, type CSSProperties } from "react";

type TypedefaultStateDefaultType = {
  buttonText?: string;

  /** Style props */
  typedefaultStateDefaultBorderRadius?: CSSProperties["borderRadius"];
  typedefaultStateDefaultBackgroundColor?: CSSProperties["backgroundColor"];
  typedefaultStateDefaultPosition?: CSSProperties["position"];
  typedefaultStateDefaultTop?: CSSProperties["top"];
  typedefaultStateDefaultLeft?: CSSProperties["left"];
  typedefaultStateDefaultBoxShadow?: CSSProperties["boxShadow"];
  typedefaultStateDefaultBorder?: CSSProperties["border"];
  typedefaultStateDefaultWidth?: CSSProperties["width"];
  typedefaultStateDefaultHeight?: CSSProperties["height"];
  continueFontSize?: CSSProperties["fontSize"];
  continueFontFamily?: CSSProperties["fontFamily"];
  continueColor?: CSSProperties["color"];
};

const TypedefaultStateDefault: FunctionComponent<
  TypedefaultStateDefaultType
> = ({
  buttonText,
  typedefaultStateDefaultBorderRadius,
  typedefaultStateDefaultBackgroundColor,
  typedefaultStateDefaultPosition,
  typedefaultStateDefaultTop,
  typedefaultStateDefaultLeft,
  typedefaultStateDefaultBoxShadow,
  typedefaultStateDefaultBorder,
  typedefaultStateDefaultWidth,
  typedefaultStateDefaultHeight,
  continueFontSize,
  continueFontFamily,
  continueColor,
}) => {
  const typedefaultStateDefaultStyle: CSSProperties = useMemo(() => {
    return {
      borderRadius: typedefaultStateDefaultBorderRadius,
      backgroundColor: typedefaultStateDefaultBackgroundColor,
      position: typedefaultStateDefaultPosition,
      top: typedefaultStateDefaultTop,
      left: typedefaultStateDefaultLeft,
      boxShadow: typedefaultStateDefaultBoxShadow,
      border: typedefaultStateDefaultBorder,
      width: typedefaultStateDefaultWidth,
      height: typedefaultStateDefaultHeight,
    };
  }, [
    typedefaultStateDefaultBorderRadius,
    typedefaultStateDefaultBackgroundColor,
    typedefaultStateDefaultPosition,
    typedefaultStateDefaultTop,
    typedefaultStateDefaultLeft,
    typedefaultStateDefaultBoxShadow,
    typedefaultStateDefaultBorder,
    typedefaultStateDefaultWidth,
    typedefaultStateDefaultHeight,
  ]);

  const continueStyle: CSSProperties = useMemo(() => {
    return {
      fontSize: continueFontSize,
      fontFamily: continueFontFamily,
      color: continueColor,
    };
  }, [continueFontSize, continueFontFamily, continueColor]);

  return (
    <div
      className="rounded-md bg-slate-900 flex flex-row py-2 px-4 items-center justify-center text-left text-sm text-white font-body-medium"
      style={typedefaultStateDefaultStyle}
    >
      <div
        className="relative leading-[24px] font-medium"
        style={continueStyle}
      >
        {buttonText}
      </div>
    </div>
  );
};

export default TypedefaultStateDefault;
