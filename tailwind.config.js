/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        floralwhite: "#fbf6ee",
        white: "#fff",
        gray: {
          "100": "rgba(255, 255, 255, 0.44)",
          "200": "rgba(0, 0, 0, 0.4)",
        },
        black: "#000",
        darkslateblue: "#153566",
      },
      spacing: {},
      fontFamily: {
        "ibm-plex-mono": "'IBM Plex Mono'",
        roboto: "Roboto",
        "source-serif-pro": "'Source Serif Pro'",
      },
      borderRadius: {
        "11xl": "30px",
        xl: "20px",
        "81xl": "100px",
      },
    },
    fontSize: {
      "3xs": "10px",
      mini: "15px",
      xs: "12px",
      sm: "14px",
      "4xl": "23px",
      lg: "18px",
      base: "16px",
      inherit: "inherit",
    },
  },
  corePlugins: {
    preflight: false,
  },
};
