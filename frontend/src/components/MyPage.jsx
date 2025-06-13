import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router";
import Paper from "@mui/material/Paper";
import Tabs from "@mui/material/Tabs";
import ManageSearchIcon from '@mui/icons-material/RestartAlt';
import Tab from "@mui/material/Tab";
import PersonPinIcon from "@mui/icons-material/PersonPin";
import AccessTimeFilledIcon from '@mui/icons-material/AccessTimeFilled';
import axios from "axios";
import dayjs from "dayjs";
import duration from "dayjs/plugin/duration";
import {CardContent, Grid, Typography, Box, Card} from "@mui/material";

dayjs.extend(duration);


function MyPage() {
    const [walks, setWalks] = useState([]);
    const userId = localStorage.getItem('userId');

    const navigate = useNavigate();
    const goMyPage = () => {
        navigate('/myPage');
    };
    const goMain = () => {
        navigate('/main');
    }


    useEffect(() => {
        const fetchWalks = async () => {
            try {
                const res = await axios.get(`/api/users/${userId}/walks`);
                setWalks(res.data);
            } catch (error) {
                console.error("Walk一覧取得失敗", error);
            }
        };
        console.log(walks)
        fetchWalks();
    }, [userId]);
    return (
        <div>
            <h2>マイページ</h2>
            <Grid container spacing={2}>
                {walks.map((walk) => {
                    if (!walk.startTime || !walk.endTime) return null;

                    const start = dayjs(walk.startTime);
                    const end = dayjs(walk.endTime);
                    const diff = end.diff(start);
                    const dur = dayjs.duration(diff);

                    return (
                        <Grid item xs={12} sm={6} md={4} key={walk.id}>
                            <Card
                                variant="outlined"
                                sx={{
                                    borderRadius: 2,
                                    boxShadow: 3,
                                    '&:hover': {boxShadow: 6, transform: 'translateY(-4px)'},
                                    transition: 'all 0.2s ease-in-out',
                                }}
                            >
                                <CardContent>
                                    {/* 登録日付 */}
                                    <Typography variant="caption" color="text.secondary">
                                        {start.format('YYYY/MM/DD')}
                                    </Typography>

                                    {/* 差分（分・秒） */}
                                    <Typography variant="h6" component="div" sx={{mt: 1}}>
                                        {`${dur.minutes()}分${dur.seconds()}秒`}
                                    </Typography>

                                    {/* 開始・終了時刻 */}
                                    <Box sx={{mt: 0.5}}>
                                        <Typography variant="body2" color="text.secondary">
                                            開始：{start.format('HH:mm')}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            終了：{end.format('HH:mm')}
                                        </Typography>
                                    </Box>
                                </CardContent>
                            </Card>
                        </Grid>
                    );
                })}
            </Grid>

            <Paper
                square
                sx={{
                    position: "fixed",
                    bottom: 0,
                    left: 0,
                    right: 0,
                    zIndex: 1000,
                    backgroundColor: "rgba(0, 0, 0, 0.5)",
                    borderRadius: "16px",
                }}
            >

                <Tabs
                    variant="fullWidth"
                    textColor="inherit"
                    TabIndicatorProps={{
                        style: {
                            backgroundColor: "#00ffd0",
                            height: "4px",
                        },
                    }}
                >

                    <Tab icon={<ManageSearchIcon sx={{color: "white"}}/>}/>
                    <Tab icon={<AccessTimeFilledIcon sx={{color: "white"}}/>} onClick={goMain}/>
                    <Tab icon={<PersonPinIcon sx={{color: "white"}}/>} onClick={goMyPage}/>
                </Tabs>
            </Paper>
        </div>
    )
}

export default MyPage;


// <Paper
//     square
//     sx={{
//         position: "fixed",
//         bottom: 0,
//         left: 0,
//         right: 0,
//         zIndex: 1000,
//         backgroundColor: "rgba(0, 0, 0, 0.5)",
//         borderRadius: "16px",
//     }}
// >
//
//     <Tabs
//         variant="fullWidth"
//         textColor="inherit"
//         TabIndicatorProps={{
//             style: {
//                 backgroundColor: "#00ffd0",
//                 height: "4px",
//             },
//         }}
//     >
//
//         <Tab icon={<ManageSearchIcon sx={{color: "white"}}/>}/>
//         <Tab icon={<AccessTimeFilledIcon sx={{color: "white"}}/>} onClick={goMain}/>
//         <Tab icon={<PersonPinIcon sx={{color: "white"}}/>} onClick={goMyPage}/>
//     </Tabs>
// </Paper>





