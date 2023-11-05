import "swagger-ui-react/swagger-ui.css";

const Swagger = () => {
  return (
    <iframe
      src="https://openjio-loadbalancer-backend-824860381.ap-southeast-1.elb.amazonaws.com/swagger-ui.html"
      width="800"
      height="600"
    ></iframe>
  );
};

export default Swagger;
