import { Link } from "react-router-dom";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect } from "react";
import axios from "axios";


export default function EventPage() {
  //Sends GET request and updates posts
  const userName = 'admin';
  const password = 'admin';

  const [postData, setPostData] = useState([] as any[]);
  const options = {
    method: 'GET',
    headers: {
      'Authorization': 'Basic ' + btoa(`${userName}:${password}`),
    },
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/events", options)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setPostData(data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  }, []);

  async function handleClick() {

    const body = {
      "name": `CS201`,
      "startDate": "2001-02-04",
      "endDate": "2001-08-10",
      "description": "Data Structures and Algorithm",
      "capacity": 45,
      "eventType": "Workshop",
      "venue": "venue",
      "registered": false,
      "visible": false
    }
    await axios.post("http://localhost:8080/api/v1/events", body, {
      headers: {
        'Authorization': 'Basic ' + btoa(`${userName}:${password}`),
      }
    })
      .catch((err) => {
        console.log(err.message);
      });
    window.location.reload();
  }

  return (
    <div>
      <NavBarTest2 />
      <div className="h-20"></div>
      <div className="flex flex-col gap-10 w-[60%] m-auto">
      <button className="m-auto cursor-pointer bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow" onClick={handleClick}>Add Event</button>
        {/* Maps post data! */}
        {postData.map((post) => {
          return (
            <div className="m-auto overflow-hidden text-center text-xl font-ibm-plex-mono" key={post.id}>
              <h2 className="post-title">{post.name}</h2>
              <p className="post-body">Description: {post.description}</p>
              <p className="post-body">From {post.startDate} to {post.endDate}</p>
              <p className="post-body">Venue at : {post.venue}</p>
              <p className="post-body">Event capacity: {post.capacity}</p>
              <TicketFooter />
            </div>
          );
        })}
      </div>
    </div>
  );
  function TicketFooter() {
    return (
      <div>
        <Link to="/purchased">
          <div className="button">
            <div className="bg-black rounded-xl text-white hover:bg-white hover:text-black shadow-md cursor-pointer">Sign Up!</div>
          </div>
        </Link>
      </div>
    );
  }


}
