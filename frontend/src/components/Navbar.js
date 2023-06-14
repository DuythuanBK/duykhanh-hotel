import { useEffect, useState } from "react";
import moment from "moment";

export default function Navbar() {
  const [date, setDate] = useState(moment().format('DD-MM-YYYY'));
  const [time, setTime] = useState(moment().format('HH:mm:ss'));
  const [timerId, setTimerId] = useState('');

  useEffect(() => {
    let id = setInterval(updateDateTime, 1000);
    setTimerId(id);
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    clearInterval(timerId);

    return () => {
      // Run on component unmount (cleanup)
    };
  }, [timerId]);

  const updateDateTime = () => {
      let curDate = moment().format('DD-MM-YYYY');
      let curTime = moment().format('HH:mm:ss');
      if(date !== curDate) {
        setDate(curDate);
      }

      if(time !== curTime) {
        setTime(curTime);
      }
  }


  return (
    <div className="row ms-2 mb-2">
        <h1>{date + ' ' + time}</h1>
    </div>
  )
}