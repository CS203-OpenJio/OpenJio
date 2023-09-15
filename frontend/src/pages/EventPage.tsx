import TicketFooter from "../components/EventPage/TicketFooter";
import NavBar from "../components/HomeScreen/NavBar";
import { useState, useEffect } from "react";

export default function EventPage() {

    //Sends GET request and updates posts
    const [postData, setPostData] = useState([] as any[]);
    useEffect(() => {
        fetch('https://jsonplaceholder.typicode.com/posts?_limit=10')
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setPostData(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);


    return (
        <div>
            <NavBar />
            <div className="h-20"></div>
            <TicketFooter />
            <div>
                {/* Maps post data! */}
                {postData.map((post) => {
                    return (
                        <div className="post-card" key={post.id}>
                            <h2 className="post-title">{post.title}</h2>
                            <p className="post-body">{post.body}</p>
                            <div className="button">
                                <div className="delete-btn">Delete</div>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};