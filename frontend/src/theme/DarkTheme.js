import { createTheme } from "@mui/material";

export const darkTheme = createTheme({
    palette: {
        mode: "dark", // This sets the theme to dark mode
        primary: {
          main: "#e91e63", // Customize the primary color to your preference
        },
        secondary: {
          main: "#242B2E", // Customize the secondary color to your preference
        },
        background: {
          main: "#000000",
          default: "#0D0D0D",
          paper: "#0D0D0D",
        },
        textColor: {
          main: "#111111",
        },
    },
});