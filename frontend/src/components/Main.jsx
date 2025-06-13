import {MapContainer, TileLayer, Polyline} from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import React, {useEffect, useState, useRef} from 'react';
import Paper from "@mui/material/Paper";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import PersonPinIcon from "@mui/icons-material/PersonPin";
import AccessTimeFilledIcon from '@mui/icons-material/AccessTimeFilled';
import ManageSearchIcon from '@mui/icons-material/ManageSearch';
import {Box, Button} from "@mui/material";
import PlayCircleFilledWhiteIcon from '@mui/icons-material/PlayCircleFilledWhite';
import StopCircleIcon from '@mui/icons-material/StopCircle';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import axios from "axios";
import Stopwatch from "./stopwatch/Stopwatch.jsx";
import RestartAltIcon from '@mui/icons-material/RestartAlt';
import {useNavigate} from "react-router";
import {Geolocation} from '@capacitor/geolocation';

// import {c} from "vite/dist/node/moduleRunnerTransport.d-DJ_mE5sf.js";

function Main() {
    const [value, setValue] = useState(1);
    const [isTracking, setIsTracking] = useState(false);
    const [walkId, setWalkId] = useState(null);
    const [walkedPath, setWalkedPath] = useState([]);
    const intervalRef = useRef(null);
    const userId = localStorage.getItem("userId");
    const [mapCenter, setMapCenter] = useState([35.12393136975013, 137.06592745231882]);

    const handleChange = (_, newValue) => setValue(newValue);
    const navigate = useNavigate();


//最新の緯度経度情報を配列で返す関数
    const getMap = async () => {
        console.log("🗺️getMap呼ばれたよ")
        try {
            const res = await axios.get(`/api/walks/${walkId}/positions`);
            const mapArray = res.data;
            // const newArray = mapArray.map(obj => [obj.lat, obj.lng]);

            const newArray = mapArray.map((obj, i) => [
                // obj.lat + 0.00110 * i,  // 北に大体10m動くようにする
                obj.lat,  // 北に大体10m動くようにする

                obj.lng
            ]);
            return newArray;

        } catch (e) {
            console.error("getMap", e)
        }
    }


    //位置を取得して送信
    const sendCurrentPosition = async () => {
        console.log("🟡️️️️️")
        // if (!walkId) return;
        try {
            const pos = await Geolocation.getCurrentPosition({enableHighAccuracy: true});
            const {latitude: lat, longitude: lng} = pos.coords;
            setWalkedPath(prev => [...prev, [lat, lng]]);
            // navigator.geolocation.getCurrentPosition(
            setMapCenter([lat, lng]);
            const res = await axios.post(`/api/walks/${walkId}/positions`, {lat, lng});
            console.log("位置情報を送信しました:", res.data);
            console.log("🟡️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥🟦")
        } catch (e) {
            console.error("️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥:", e);
        }
        // console.log("けいど", lat)
        // console.log("いど", lng)
        // console.log("🟡️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥")

        // async (position) => {
        //    const lat = position.coords.latitude;
        //    const lng = position.coords.longitude;
        //
        //   setWalkedPath((prev) => [...prev, [lat, lng]]);

        // try {
        //     const res = await axios.post(`/api/walks/${walkId}/positions`, {lat, lng});
        //     console.log("位置情報を送信しました:", res.data);
        //     console.log("🟡️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️️🟥🟦")
        // } catch (err) {
        //     console.error("送信エラー:", err);
        // }


    };

    // 🚶‍♂️スタート処理
    const startTracking = async () => {
        function Main() {
            const [value, setValue] = useState(1);
            const [isTracking, setIsTracking] = useState(false);
            const [walkId, setWalkId] = useState(null);
            const [walkedPath, setWalkedPath] = useState([]);
            const intervalRef = useRef(null);
            const userId = localStorage.getItem("userId");
            const [mapCenter, setMapCenter] = useState([35.17050525274788, 136.88623189992626]);//ミットランドスクエア

            const handleChange = (_, newValue) => setValue(newValue);
            const navigate = useNavigate();

//最新の緯度経度情報を配列で返す関数
            const getMap = async () => {
                console.log("🗺️getMap呼ばれたよ")
                try {
                    const res = await axios.get(`/api/walks/${walkId}/positions`);
                    const mapArray = res.data;
                    // const newArray = mapArray.map(obj => [obj.lat, obj.lng]);

                    const newArray = mapArray.map((obj, i) => [
                        obj.lat + 0.00110 * i,  // 北に大体10m動くようにする
                        obj.lng
                    ]);
                    return newArray;

                } catch (e) {
                    console.error("getMap", e)
                }
            }


            //位置を取得して送信
            const sendCurrentPosition = async () => {
                if (!walkId) return;

                // navigator.geolocation.getCurrentPosition(
                //     async (position) => {
                //         const lat = position.coords.latitude;
                //         const lng = position.coords.longitude;

                const pos = await Geolocation.getCurrentPosition({enableHighAccuracy: true, timeout: 10000});
                const {latitude: lat, longitude: lng} = pos.coords;

                setWalkedPath((prev) => [...prev, [lat, lng]]);

                try {
                    const res = await axios.post(`/api/walks/${walkId}/positions`, {lat, lng});
                    console.log("位置情報を送信しました:", res.data);
                } catch (err) {
                    console.error("送信エラー:", err);
                }
                // },
                //     (err) => console.error("位置取得失敗:", err)
                // );
            };

            // 🚶‍♂️スタート処理
            const startTracking = async () => {
                console.log("🚶‍♂️スタート")
                try {
                    const res = await axios.post("/api/walks", {userId});
                    const newId = res.data.id;
                    setWalkId(newId);
                    setWalkedPath([]);
                    setIsTracking(true);
                } catch (e) {
                    console.error("散歩開始失敗", e);
                }
            };

            // 🛑終了処理
            const stopTracking = async () => {
                console.log("🛑終了")
                console.log("🛑 walkId = ", walkId);
                setIsTracking(false);
                clearInterval(intervalRef.current);
                intervalRef.current = null;
                try {
                    await axios.put(`/api/walks/${walkId}/end`);
                } catch (e) {
                    console.error("終了エラー", e);
                }
            };

            const goMyPage = () => {
                navigate('/myPage');
            };
            const goMain = () => {
                navigate('/main');
            }

            //  追跡のON/OFF
            useEffect(() => {
                if (isTracking && walkId) {
                    sendCurrentPosition();
                    intervalRef.current = setInterval(() => {
                        sendCurrentPosition();
                        const mapArray = async () => {
                            const array = await getMap();
                            await setWalkedPath(array)
                        }
                        mapArray()

                        async function recordPosition() {
                            const pos = await Geolocation.getCurrentPosition();
                            console.log(pos.coords.latitude, pos.coords.longitude);
                            setMapCenter([pos.coords.latitude, pos.coords.longitude]);
                        }

                        recordPosition()
                        //❓プラグイン使っても無理だった
                        // // navigator.geolocation.getCurrentPosition(
                        // //     (position) => {
                        // //         const lat = position.coords.latitude;
                        // //         const lng = position.coords.longitude;
                        // //         setMapCenter([lat, lng]); // 現在地を中心に
                        // //     }
                        // )
                    }, 3000);
                } else {
                    clearInterval(intervalRef.current);
                }

                return () => clearInterval(intervalRef.current); // cleanup
            }, [isTracking, walkId]);


            return (
                <main>
                    <MapContainer center={mapCenter} zoom={3} style={{height: '450px', width: '100%'}}>
                        <TileLayer
                            attribution='&copy; OpenStreetMap contributors'
                            url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
                        />
                        <Polyline positions={walkedPath} pathOptions={{
                            color: 'red',
                            weight: 8,
                            dashArray: '10,10',
                            lineCap: 'round'
                        }}/>

                    </MapContainer>
                    <Stopwatch isTracking={isTracking}/>
                    <Box
                        sx={{
                            display: "flex",
                        }}
                    >
                        <Button
                            variant="contained"
                            color="primary"
                            size="large"
                            onClick={startTracking}
                            sx={{borderRadius: 999}}
                            disabled={isTracking}
                        >
                            <PlayCircleFilledWhiteIcon/>
                            スタート
                        </Button>

                        <Button
                            variant="contained"
                            color="primary"
                            size="large"
                            onClick={stopTracking}
                            sx={{borderRadius: 999}}
                            disabled={!isTracking}
                        >
                            <StopCircleIcon/>
                            ストップ
                        </Button>

                        <Button
                            variant="contained"
                            color="primary"
                            size="small"
                            sx={{borderRadius: 999}}
                            disabled={isTracking}
                            onClick={() => {
                                location.reload();
                            }}
                        >
                            <RestartAltIcon/>
                            リセット
                        </Button>
                    </Box>

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
                            value={value}
                            onChange={handleChange}
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
                </main>
            );
        }


        console.log("🚶‍♂️スタート")
        try {
            const res = await axios.post("/api/walks", {userId});
            const newId = res.data.id;
            setWalkId(newId);
            setWalkedPath([]);
            setIsTracking(true);
        } catch (e) {
            console.error("散歩開始失敗", e);
        }
    };

    // 🛑終了処理
    const stopTracking = async () => {
        console.log("🛑終了")
        console.log("🛑 walkId = ", walkId);
        setIsTracking(false);
        clearInterval(intervalRef.current);
        intervalRef.current = null;
        try {
            await axios.put(`/api/walks/${walkId}/end`);
        } catch (e) {
            console.error("終了エラー", e);
        }
    };

    const goMyPage = () => {
        navigate('/myPage');
    };
    const goMain = () => {
        navigate('/main');
    }

    // 追跡のON/OFF
    useEffect(() => {
        if (isTracking && walkId) {
            sendCurrentPosition();
            intervalRef.current = setInterval(() => {
                sendCurrentPosition();
                const mapArray = async () => {
                    const array = await getMap();
                    await setWalkedPath(array)
                }
                mapArray()

                async function recordPosition() {
                    const pos = await Geolocation.getCurrentPosition();
                    console.log(pos.coords.latitude, pos.coords.longitude);
                    setMapCenter([pos.coords.latitude, pos.coords.longitude]);
                }

                recordPosition()
                // navigator.geolocation.getCurrentPosition(
                //     (position) => {
                //         const lat = position.coords.latitude;
                //         const lng = position.coords.longitude;
                //         setMapCenter([lat, lng]); // 現在地を中心に
                //     }
                // )
            }, 5000);
        } else {
            clearInterval(intervalRef.current);
        }

        return () => clearInterval(intervalRef.current); // cleanup
    }, [isTracking, walkId]);


    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
                gap: 2,
            }}
        >
            <MapContainer center={mapCenter} zoom={16} style={{height: '450px', width: '100%'}}>
                <TileLayer
                    attribution='&copy; OpenStreetMap contributors'
                    url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
                />
                <Polyline positions={walkedPath} pathOptions={{
                    color: 'red',
                    weight: 8,
                    dashArray: '10,10',
                    lineCap: 'round'
                }}/>

            </MapContainer>
            <Stopwatch isTracking={isTracking}/>
            <Box
                sx={{
                    display: "flex",
                }}
            >
                <Button
                    variant="contained"
                    color="primary"
                    size="large"
                    onClick={startTracking}
                    sx={{borderRadius: 999}}
                    disabled={isTracking}
                >
                    <PlayCircleFilledWhiteIcon/>
                    スタート
                </Button>

                <Button
                    variant="contained"
                    color="primary"
                    size="large"
                    onClick={stopTracking}
                    sx={{borderRadius: 999}}
                    disabled={!isTracking}
                >
                    <StopCircleIcon/>
                    ストップ
                </Button>

                <Button
                    variant="contained"
                    color="primary"
                    size="small"
                    sx={{borderRadius: 999}}
                    disabled={isTracking}
                    onClick={() => {
                        location.reload();
                    }}
                >
                    <RestartAltIcon/>
                    リセット
                </Button>
            </Box>

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
                    value={value}
                    onChange={handleChange}
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
        </Box>
    );
}

export default Main;
