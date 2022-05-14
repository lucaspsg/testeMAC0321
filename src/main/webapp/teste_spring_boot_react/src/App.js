import logo from "./logo.svg";
import "./App.css";

function App() {
  return (
    <div className="App">
      <h1>Trabalho MAC0321</h1>
      <button onClick={LandingPage}>Iniciar</button>
    </div>
  );
}

export default App;

function LandingPage() {
  const getSpotifyUserLogin = () => {
    fetch("http://localhost:8080/api/login")
      .then((response) => response.text())
      .then((response) => {
        window.location.replace(response);
      });
  };
  getSpotifyUserLogin();
}
