import logo from './logo.svg';
import './App.css';
import { Navbar } from './component/Navbar/Navbar'
import { CssBaseline, ThemeProvider } from '@mui/material';
import { darkTheme } from './theme/DarkTheme';

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline/>
        <Navbar/>
    </ThemeProvider>
  );
}

export default App;
