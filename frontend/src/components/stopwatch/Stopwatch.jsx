import React, {useEffect, useState} from "react";
import "./Stopwatch.css";
import {Button} from "@mui/material";
import StopwatchDisplay from "./StopwatchDisplay.jsx";

const Stopwatch = ({isTracking}) => {
    const [isRunning, setIsRunning] = useState(false);
    const [value, setValue] = useState(0);

    useEffect(() => {
        setIsRunning(isTracking)

        let interval = null;

        if (isRunning) {
            interval = setInterval(() => {
                setValue((prevState) => prevState + 10);
            }, 10);
        } else {
            clearInterval(interval);
        }

        return () => {
            clearInterval(interval);
        };
    }, [isRunning, isTracking]);

    return (
        <div className="stopwatch-container">
            <StopwatchDisplay value={value}/>
            {/*<div className="button-container">*/}
            {/*    {isRunning ? (*/}
            {/*        <Button*/}
            {/*            variant="outlined"*/}
            {/*            color="error"*/}
            {/*            onClick={() => {*/}
            {/*                setIsRunning(false);*/}
            {/*            }}*/}
            {/*        >*/}
            {/*            Stop*/}
            {/*        </Button>*/}
            {/*    ) : (*/}
            {/*        <Button*/}
            {/*            variant="outlined"*/}
            {/*            onClick={() => {*/}
            {/*                setIsRunning(isTracking);*/}

            {/*                // setIsRunning(true);*/}
            {/*            }}*/}
            {/*        >*/}
            {/*            Start*/}
            {/*        </Button>*/}
            {/*    )}*/}
            {/*    <Button*/}
            {/*        variant="outlined"*/}
            {/*        onClick={() => {*/}
            {/*            setValue(0);*/}
            {/*        }}*/}
            {/*    >*/}
            {/*        Reset*/}
            {/*    </Button>*/}
            {/*</div>*/}
        </div>
    );
};

export default Stopwatch;
