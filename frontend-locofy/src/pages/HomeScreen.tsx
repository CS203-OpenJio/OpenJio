import { FunctionComponent } from "react";
import Header2 from "../components/Header2";
import Header3 from "../components/Header3";
import Header from "../components/Header";

const HomeScreen: FunctionComponent = () => {
  return (
    <div>
      <Header/>
      <div>
        <Header2 />
          <p className="overflow-hidden text-left text-11xl text-black font-ibm-plex-mono m-0">
               OpenJio is a powerful and user-friendly events website tailored
               for university students, designed to enhance the way you
                discover, create, and participate in events on your campus.
                We are constantly innovating and in search of ways to improve
                 our application and to provide our users the most seamless
                 experience. We welcome constructive feedback and your
                 suggestions to improve our application and user-experience are
                 greatly appreciated. Do stay tuned for more updates!
          </p>
        <Header3 />
      </div>
    </div>
    
  );
};

export default HomeScreen;
