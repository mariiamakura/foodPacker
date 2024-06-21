import React from 'react';
import { AppBar, Toolbar, IconButton, Typography, Button, Badge } from '@mui/material';
import { Search as SearchIcon, ShoppingCart as ShoppingCartIcon } from '@mui/icons-material';
import PermIdentityIcon from '@mui/icons-material/PermIdentity';
import "./Navbar.css"

export const Navbar = () => {
  return (
    <AppBar position="static">
      <Toolbar 
       sx={{
        backgroundColor: 'rgba(255, 182, 138, 0.8)', // pastel orange color
      }}>
        <Typography variant="h6" 
        component="div" 
        sx={{ flexGrow: 1 }}>
          FoodPacker
        </Typography>
        <IconButton color="inherit" aria-label="search">
          <SearchIcon />
        </IconButton>
        <IconButton color="inherit" aria-label="perm identity icon">
          <PermIdentityIcon />
        </IconButton>
        <IconButton color="inherit" aria-label="shopping cart">
          <Badge color="secondary" badgeContent={2}>
            <ShoppingCartIcon />
          </Badge>
        </IconButton>
      </Toolbar>
    </AppBar>
  );
}

