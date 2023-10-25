import NavBarLite from '../components/HomeScreen/NavBarLite';
import { Link } from 'react-router-dom';
import LoginButton from '../components/HomeScreen/LoginButton';

const Unauthorized = () => {
    return (
        <>
            <NavBarLite />
            <div className="flex flex-col items-center justify-center h-screen font-ibm-plex-mono">
                <h1 className="text-4xl font-bold mb-8">Unauthorized</h1>
                <p className="text-lg mb-4">Please <Link to={'/login'}><LoginButton /></Link> to access this page.</p>
            </div>
        </>
    );
};

export default Unauthorized;
