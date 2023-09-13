/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        "slate-900": "#0f172a",
        white: "#fff",
        floralwhite: "#fbf6ee",
        darkslateblue: "#153566",
        black: "#000",
        gray: "rgba(0, 0, 0, 0.4)",
      },
      spacing: {},
      fontFamily: {
        "body-medium": "Inter",
        "source-serif-pro": "'Source Serif Pro'",
        "ibm-plex-mono": "'IBM Plex Mono'",
        "roboto-serif": "'Roboto Serif'",
        "figma-hand": "'Figma Hand'",
      },
      borderRadius: {
        "3xs": "10px",
        "11xl": "30px",
        "6xs": "7px",
      },
    },
    fontSize: {
      sm: "14px",
      "51xl": "70px",
      "21xl": "40px",
      "11xl": "30px",
      "6xl": "25px",
      "31xl": "50px",
      xl: "20px",
      "4xl": "23px",
      inherit: "inherit",
    },
  },
  corePlugins: {
    preflight: false,
  },
};
