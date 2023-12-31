const { fontFamily } = require("tailwindcss/defaultTheme");

/** @type {import('tailwindcss').Config} */
module.exports = {
  variants: {
    extend: {
      transform: ["active"],
    },
  },

  darkMode: ["class"],
  content: ["./src/**/*.{js,jsx,ts,tsx}", "./src/**/**/*.{js,jsx,ts,tsx}"],
  theme: {
    container: {
      center: true,
      padding: "2rem",
      screens: {
        "2xl": "1400px",
      },
    },
    extend: {
      colors: {
        "slate-900": "#0f172a",
        white: "#fff",
        floralwhite: "#fbf6ee",
        darkslateblue: "#153566",
        black: "#000",
        gray: "rgba(0, 0, 0, 0.4)",
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
        background: "hsl(var(--background))",
        foreground: "hsl(var(--foreground))",
        primary: {
          DEFAULT: "hsl(var(--primary))",
          foreground: "hsl(var(--primary-foreground))",
        },
        secondary: {
          DEFAULT: "hsl(var(--secondary))",
          foreground: "hsl(var(--secondary-foreground))",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "hsl(var(--accent))",
          foreground: "hsl(var(--accent-foreground))",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
      },
      spacing: {},
      fontFamily: {
        "body-medium": "Inter",
        "source-serif-pro": "'Source Serif Pro'",
        roboto: "Roboto",
        "ibm-plex-mono": "'IBM Plex Mono'",
        "roboto-serif": "'Roboto Serif'",
        pacifico: "'Pacifico'",
        sans: ["var(--font-sans)", ...fontFamily.sans],
      },
      borderRadius: {
        "3xs": "10px",
        "11xl": "30px",
        "6xs": "7px",
        xl: "20px",
        "81xl": "100px",
        lg: `var(--radius)`,
        md: `calc(var(--radius) - 2px)`,
        sm: "calc(var(--radius) - 4px)",
      },
      keyframes: {
        "accordion-down": {
          from: { height: 0 },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        "accordion-up": {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: 0 },
        },
      },
      animation: {
        "accordion-down": "accordion-down 0.2s ease-out",
        "accordion-up": "accordion-up 0.2s ease-out",
      },
    },
    fontSize: {
      "3xs": "10px",
      mini: "15px",
      xs: "12px",
      lg: "18px",
      sm: "14px",
      base: "16px",
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
  plugins: [require("tailwindcss-animate")],
};
