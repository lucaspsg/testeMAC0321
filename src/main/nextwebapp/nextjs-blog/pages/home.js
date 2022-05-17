import Head from "next/head";
import { useState, setState, useEffect } from "react";
import axios from "axios";

export default function Home(props) {
  const [playlists, setPlaylists] = useState();
  const [musicas, setMusicas] = useState();
  const [nomePlaylist, setNomePlaylist] = useState();
  const [busca, setBusca] = useState();
  const [buscaResultado, setBuscaResultado] = useState();
  const [playlistId, setPlaylistId] = useState();
  const [escondido, setEscondido] = useState(true);

  let userId = "";

  

  useEffect(() => {
    fetch("http://localhost:8080/playlists")
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setPlaylists(data);
      });
      fetch("http://localhost:8080/home");
  }, []);

  return (
    <div className="container">
      <Head>
        <title>Create Next App</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main>
        <h1 className="title">Gerenciador de Playlists</h1>

        {playlists?.map((playlist) => {
          return (
            <button
              key={playlist.id}
              className="card"
              onClick={() => {
                fetch(`http://localhost:8080/playlist-items/${playlist.id}`)
                  .then((response) => response.json())
                  .then((data) => {
                    console.log(data);
                    setMusicas(data);
                    setPlaylistId(playlist.id);
                    console.log(playlistId);
                    setEscondido(false);
                  });
              }}
            >
              <h3 key={playlist.name}>
                {playlist.name};
              </h3>
            </button>
          );
        })}
        <div className="card">
          <h3>Criar Playlist</h3>
          <form>
            <input type="text" onChange={(e) => {
              setNomePlaylist(e.target.value);
              console.log(nomePlaylist);
            }}/>
          </form>
          <button onClick={() => {
          fetch(`http://localhost:8080/create-playlist/${nomePlaylist}`)
          .then((response) => response.json())
          .then((data) => {
            setPlaylists([...playlists, data]);
          });
        }}>buscar</button>
        </div>
      </main>
      <div className="itemsHeader">
        <h2>Musicas da playlist selecionada</h2>
        {musicas?.map((musica) => {
          return (
          <div key={musica.id}>
              <p>
                {musica.track.name} - {musica.track.album.artists[0].name}
              </p>
              <button onClick={() => {
                fetch(`http://localhost:8080/remove-playlist-items/${playlistId}/${musica.track.uri}`, { mode: 'no-cors'});
                console.log(`http://localhost:8080/remove-playlist-items/${playlistId}/${musica.track.uri}`);
              }}>remover</button>
          </div>
          );
        })}
      </div>
      <div className="buscarMusica" hidden={escondido}>
        <h3>Buscar musica</h3>
        <form>
          <input type="text" onChange={(e) => {
            setBusca(e.target.value);
            console.log(busca);
          }}/>
        </form>
        <button onClick={() => {
          fetch(`http://localhost:8080/search/${busca}`)
          .then((response) => response.json())
          .then((data) => {
            setBuscaResultado(data)
          });
        }}>buscar</button>
        {buscaResultado?.map((musica)  => {
          return (
            <div key={musica.id}>
          <p>{musica.name} - {musica.artists[0].name}</p>
          <button onClick={() => {
                fetch(`http://localhost:8080/add-playlist-items/${playlistId}/${musica.uri}`, { mode: 'no-cors'});
              }}>adicionar</button>
          </div>
          )
        })}
      </div>

      <footer>
        <a
          href="https://vercel.com?utm_source=create-next-app&utm_medium=default-template&utm_campaign=create-next-app"
          target="_blank"
          rel="noopener noreferrer"
        >
          Powered by <img src="/vercel.svg" alt="Vercel" className="logo" />
        </a>
      </footer>

      <style jsx>{`
        .container {
          min-height: 100vh;
          padding: 0 0.5rem;
          display: flex;
          flex-direction: column;
          justify-content: flex-start;
          align-items: flex-start;
        }

        main {
          padding: 5rem 0;
          flex: 1;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        }

        footer {
          width: 100%;
          height: 100px;
          border-top: 1px solid #eaeaea;
          display: flex;
          justify-content: center;
          align-items: center;
        }

        footer img {
          margin-left: 0.5rem;
        }

        footer a {
          display: flex;
          justify-content: center;
          align-items: center;
        }

        a {
          color: inherit;
          text-decoration: none;
        }

        .title a {
          color: #0070f3;
          text-decoration: none;
        }

        .title a:hover,
        .title a:focus,
        .title a:active {
          text-decoration: underline;
        }

        .title {
          margin: 0;
          line-height: 1.15;
          font-size: 4rem;
        }

        .title,
        .description {
          text-align: center;
        }

        .description {
          line-height: 1.5;
          font-size: 1.5rem;
        }

        .itemsHeader {
          position: absolute;
          top: 80px;
          right: 200px;
        }

        .buscarMusica{
          position: absolute;
          top: 500px;
          right: 200px;
          height: 500px;
        }

        code {
          background: #fafafa;
          border-radius: 5px;
          padding: 0.75rem;
          font-size: 1.1rem;
          font-family: Menlo, Monaco, Lucida Console, Liberation Mono,
            DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace;
        }

        .grid {
          display: flex;
          align-items: center;
          justify-content: center;
          flex-wrap: wrap;

          max-width: 800px;
          margin-top: 3rem;
          padding: 0px;
        }

        .card {
          margin: 1rem;
          flex-basis: 45%;
          padding: 1.5rem;
          text-align: left;
          color: inherit;
          text-decoration: none;
          border: 1px solid #eaeaea;
          border-radius: 10px;
          transition: color 0.15s ease, border-color 0.15s ease;
        }

        .card:hover,
        .card:focus,
        .card:active {
          color: #0070f3;
          border-color: #0070f3;
        }

        .card h3 {
          margin: 0 0 1rem 0;
          font-size: 1.5rem;
        }

        .card p {
          margin: 0;
          font-size: 1.25rem;
          line-height: 1.5;
        }

        .logo {
          height: 1em;
        }

        @media (max-width: 600px) {
          .grid {
            width: 100%;
            flex-direction: column;
          }
        }
      `}</style>

      <style jsx global>{`
        html,
        body {
          padding: 0;
          margin: 0;
          font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto,
            Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue,
            sans-serif;
        }

        * {
          box-sizing: border-box;
        }
      `}</style>
    </div>
  );
}
