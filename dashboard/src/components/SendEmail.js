import React, { useState } from 'react';
import { TextField, Button, Container, Typography, Box, Alert, Select, MenuItem, FormControl, InputLabel, Input, IconButton, List, ListItem, ListItemText, ListItemSecondaryAction, Card, CardContent } from '@mui/material';
import { Delete, Send as SendIcon, AttachFile } from '@mui/icons-material';
import axios from 'axios';

const SendEmail = () => {
  const [to, setTo] = useState('');
  const [subject, setSubject] = useState('');
  const [body, setBody] = useState('');
  const [template, setTemplate] = useState('');
  const [templateVariables, setTemplateVariables] = useState({});
  const [attachments, setAttachments] = useState([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const templateFields = {
    'welcome-email': ['name', 'username', 'email'],
    'sample-template': ['name']
  };

  const handleTemplateChange = (e) => {
    const selectedTemplate = e.target.value;
    setTemplate(selectedTemplate);
    setTemplateVariables({});
  };

  const handleVariableChange = (key, value) => {
    setTemplateVariables(prev => ({ ...prev, [key]: value }));
  };

  const handleFileChange = (e) => {
    const files = Array.from(e.target.files);
    files.forEach(file => {
      const reader = new FileReader();
      reader.onload = () => {
        setAttachments(prev => [...prev, { name: file.name, data: reader.result.split(',')[1] }]);
      };
      reader.readAsDataURL(file);
    });
  };

  const removeAttachment = (index) => {
    setAttachments(prev => prev.filter((_, i) => i !== index));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    const token = localStorage.getItem('token');
    try {
      await axios.post('http://localhost:8080/api/email/send', {
        to,
        subject,
        body,
        templateName: template || null,
        templateVariables: template ? templateVariables : null,
        attachments: attachments.map(att => att.data),
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setSuccess('Email sent successfully!');
      setError('');
      setTo('');
      setSubject('');
      setBody('');
      setTemplate('');
      setTemplateVariables({});
      setAttachments([]);
    } catch (err) {
      setError('Failed to send email');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="md">
      <Box sx={{ mt: 8, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography component="h1" variant="h5">
          Send Email
        </Typography>
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1, width: '100%' }}>
          <TextField
            margin="normal"
            required
            fullWidth
            label="To"
            type="email"
            value={to}
            onChange={(e) => setTo(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            label="Subject"
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            multiline
            rows={4}
            label="Body"
            value={body}
            onChange={(e) => setBody(e.target.value)}
          />
          <FormControl fullWidth margin="normal">
            <InputLabel>Template (Optional)</InputLabel>
            <Select
              value={template}
              onChange={handleTemplateChange}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value="welcome-email">Welcome Email</MenuItem>
              <MenuItem value="sample-template">Sample Template</MenuItem>
            </Select>
          </FormControl>
          {template && templateFields[template] && (
            <Box sx={{ mt: 2 }}>
              <Typography variant="h6">Template Variables</Typography>
              {templateFields[template].map(field => (
                <TextField
                  key={field}
                  margin="normal"
                  fullWidth
                  label={field.charAt(0).toUpperCase() + field.slice(1)}
                  value={templateVariables[field] || ''}
                  onChange={(e) => handleVariableChange(field, e.target.value)}
                />
              ))}
            </Box>
          )}
          <FormControl fullWidth margin="normal">
            <InputLabel shrink>Attachments</InputLabel>
            <Input
              type="file"
              multiple
              onChange={handleFileChange}
              inputProps={{ accept: '*' }}
            />
          </FormControl>
          {attachments.length > 0 && (
            <List>
              {attachments.map((att, index) => (
                <ListItem key={index}>
                  <ListItemText primary={att.name} />
                  <ListItemSecondaryAction>
                    <IconButton edge="end" onClick={() => removeAttachment(index)}>
                      <Delete />
                    </IconButton>
                  </ListItemSecondaryAction>
                </ListItem>
              ))}
            </List>
          )}
          {error && <Alert severity="error">{error}</Alert>}
          {success && <Alert severity="success">{success}</Alert>}
          <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} disabled={loading} startIcon={<SendIcon />}>
            {loading ? 'Sending...' : 'Send Email'}
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default SendEmail;
