import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, Button, Typography } from '@mui/material';

export default function StartForm() {
    const navigate = useNavigate();

    const handleStart = () => {
        navigate('/login');
    };

    return (
        <Box
            sx={{
                height: '100vh',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
            }}
        >
            <Box
                sx={{
                    textAlign: 'center',
                    color: 'white',
                }}
            >
                <Typography variant="h2" fontWeight="bold" mb={3}>
                    WALK MEMORY
                </Typography>
                <Button
                    variant="contained"
                    color="primary"
                    size="large"
                    onClick={handleStart}
                    sx={{ borderRadius: 999 }}
                >
                    はじめる
                </Button>
            </Box>
        </Box>
    );
}