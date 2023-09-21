import { Link } from "react-router-dom";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect } from "react";


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

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/events", options)
      .then((response) => response.json())
      .then((data) => {
        console.log("hi");
        console.log(data);
        setPostData(data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  }, []);

  return (
    <div>
      <NavBarTest2 />
      <div className="h-20"></div>
      <div className="flex flex-col gap-10 w-[60%] m-auto">
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
}
