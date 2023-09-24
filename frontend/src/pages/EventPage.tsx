import { Link } from "react-router-dom";
import NavBarTest2 from "../components/CentralHub/Section1parts/NavBarTest2";
import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function EventPage() {
  //Sends GET request and updates posts
  const userName = "admin";
  const password = "admin";
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

  //   // called whenever Add Event is clicked, sends POST event with the event data.
  //   // i removed this and put under Event Form
  //   async function handleClick() {
  //     const body = {
  //       name: `.Hack MERN Stack Series (MESS) 2022`,
  //       startDate: "2022-12-29",
  //       endDate: "2023-12-30",
  //       description:
  //         "Interested in developing your own application but don't know how to start? Kickstart your software development journey with .Hack's first-ever winter program: MERN Stack Series (MESS). Fulfill your curiosity by attending our exclusive workshops about the production-ready and widely used technological framework of MongoDB, Express, ReactJS, and NodeJS. Apply the insights gained throughout the workshop to build your own portfolio website ",
  //       capacity: 100,
  //       eventType: "Workshop",
  //       venue: "YPHSL Seminar Room 3-09 & 3-12",
  //       registered: false,
  //       visible: false,
  //     };
  //     await axios
  //       .post("http://localhost:8080/api/v1/events", body, {
  //         headers: {
  //           Authorization: "Basic " + btoa(`${userName}:${password}`),
  //         },
  //       })
  //       .catch((err) => {
  //         console.log(err.message);
  //       });
  //     window.location.reload();
  //   }

  // i removed the Add Event Button and put it into EventForm too

  return (
    <div>
      <NavBarTest2 />
      <div className="h-20"></div>
      <div className="gap-10 w-auto m-4">
        {/* Maps post data! */}
        {postData.map((post) => {
          return (
            <div className="" key={post.id}>
              <div className="flex flex-col justify-normal items-center font-ibm-plex-mono ml-14 mr-14">
                <h2 className="text-31xl">{post.name}</h2>
                <div className="flex grow bg-white font-normal text-4xl p-3 border border-solid border-black rounded-lg m-4">
                  <div className="">
                    Date: {post.startDate} to {post.endDate}
                  </div>

                  <div className=""> Venue: {post.venue} </div>
                  <div className=""> Max Event Capacity: {post.capacity} </div>
                </div>
                <div className="flex grow text-4xl font-light bg-white border border-solid border-black rounded-lg p-3 m-4">
                  {post.description}
                </div>

                <TicketFooter id={post.id} />
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
  function TicketFooter({ id }: { id: number }) {
    const body = {
      studentId: userID,
      eventId: id,
      isDeregistered: false,
      isSuccessful: false,
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
        <Link to="/purchased" state={{ TID: id }}>
          <div className="button">
            <button
              onClick={handleClick}
              className="mt-3 bg-white hover:translate hover:bg-black hover:text-white font-semibold py-2 px-4 border border-gray-400 rounded shadow text-3xl font-ibm-plex-mono cursor-pointer transform active:scale-75 transition-transform"
            >
              Sign Up!
            </button>
          </div>
        </Link>
      </div>
    );
  }
}
