import React, { useState } from 'react';
import { AppBar, Toolbar, Typography, Button, Container, Box } from '@mui/material';
import { Send as SendIcon, Email as EmailIcon, Logout as LogoutIcon, Dashboard as DashboardIcon } from '@mui/icons-material';
import SendEmail from './SendEmail';
import EmailLogs from './EmailLogs';

const Dashboard = ({ onLogout }) => {
  const [view, setView] = useState('send');

  const handleLogout = () => {
    localStorage.removeItem('token');
    onLogout();
  };

  return (
    <div>
      <AppBar position="static" sx={{ backgroundColor: '#1976d2' }}>
        <Toolbar>
          <DashboardIcon sx={{ mr: 2 }} />
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Email Service Dashboard
          </Typography>
          <Button color="inherit" startIcon={<SendIcon />} onClick={() => setView('send')}>Send Email</Button>
          <Button color="inherit" startIcon={<EmailIcon />} onClick={() => setView('logs')}>Email Logs</Button>
          <Button color="inherit" startIcon={<LogoutIcon />} onClick={handleLogout}>Logout</Button>
        </Toolbar>
      </AppBar>
      <Container maxWidth="lg">
        <Box sx={{ mt: 4 }}>
          {view === 'send' && <SendEmail />}
          {view === 'logs' && <EmailLogs />}
        </Box>
      </Container>
    </div>
  );
};

export default Dashboard;
