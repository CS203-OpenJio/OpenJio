import { Link } from "react-router-dom";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../App";

export default function EventPage() {

  const {user, setUser} = useContext(AuthContext);
  const userName = user.username;
  const password = user.username;
  //need to store id somewhere
  const userID = 1;

  const [postData, setPostData] = useState([] as any[]);
  const options = {
    method: "GET",
    headers: {
      Authorization: "Basic " + btoa(`${userName}:${password}`),
    },
  };

  // does a GET request, sets it in PostData variable
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

  // called whenever Add Event is clicked, sends POST event with the event data
  async function handleClick() {
    const body = {
      name: `Ellipsis Back2Sku Welfare Drive`,
      startDate: "2023-08-16",
      endDate: "2023-08-16",
      description:
        "Got the back-to-school blues? 🎒💔 Well, you don’t have to dwell on it – because Ellipsis is ready to banish them with our upcoming Back2Sku Welfare Drive!",
      capacity: 100,
      eventType: "Welfare Drive",
      venue: "SCIS1 Basement",
      registered: false,
      visible: false,
    };
    await axios
      .post("http://localhost:8080/api/v1/events", body, {
        headers: {
          Authorization: "Basic " + btoa(`${userName}:${password}`),
        },
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
      <div className="gap-10 w-[60%] m-auto">
        <div className="font-ibm-plex-mono mb-5">
          Welcome <span className="text-xl font-bold">{user.username}</span>!
        </div>
        <button
          className="m-auto items-center cursor-pointer bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-400 rounded shadow"
          onClick={handleClick}
        >
          Add Event
        </button>
        {/* Maps post data! */}
        {postData.map((post) => {
          return (
            <div
              className="flex flex-col justify-normal items-center font-ibm-plex-mono"
              key={post.id}
            >
              <h2 className="text-31xl w-[1350]">{post.name}</h2>
              <div className="bg-white font-normal text-4xl p-3 border border-solid border-black rounded-lg m-4">
                <div className="">
                  Date: {post.startDate} to {post.endDate}
                </div>
                <div className="">Venue: {post.venue}</div>
                <div className="">Max Event Capacity: {post.capacity}</div>
              </div>
              <div className="text-4xl w-auto font-light bg-white border border-solid border-black rounded-lg p-3 m-4">
                {post.description}
              </div>
              <TicketFooter id={post.id} />
            </div>
          );
        })}
      </div>
    </div>
  );
  function TicketFooter({ id }: { id: number }) {
    const body = {
        studentId:userID,
        eventId:id,
        isDeregistered:false,
        isSuccessful:false
    };

    async function handleClick() {
      await axios
      .post("http://localhost:8080/api/v1/register", body, {
        headers: {
          Authorization: "Basic " + btoa(`${userName}:${password}`),
        },
      })
      .catch((err) => {
        console.log(err.message);
      });
    }

    return (
      <div>
        <Link
          to='/purchased' state={{ TID: id }}>
          <div className="button">
            <button
              onClick={handleClick}
              className="bg-white hover:translate hover:bg-black hover:text-white font-semibold py-2 px-4 border border-gray-400 rounded shadow text-3xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform"
            >
              Sign Up!{id}
            </button>
          </div>
        </Link>
      </div>
    );
  }
}
