import React, { PureComponent } from "react";
import PropTypes from "prop-types";

class RandomPicker extends React.PureComponent {
  constructor() {
    super();

    this.state = {
      isRunning: false,
      currentChoice: "",
    };

    this.interval = null;
    this.intervalDuration = 25;
    this.duration = 1000;

    this.start = this.start.bind(this);
    this.stop = this.stop.bind(this);
    this.reset = this.reset.bind(this);
    this.pickChoice = this.pickChoice.bind(this);
    this.setChoice = this.setChoice.bind(this);
  }

  start() {
    clearInterval(this.interval);
    this.interval = setInterval(this.setChoice, this.intervalDuration);
    this.setState({ isRunning: true });
    setTimeout(() => {
      if (this.state.isRunning) {
        this.stop();
      }
    }, this.duration);
  }

  stop() {
    clearInterval(this.interval);
    this.setState({ isRunning: false });
  }

  reset() {
    clearInterval(this.interval);
    this.setState({ isRunning: false, currentChoice: "" });
  }

  pickChoice() {
    const { items } = this.props;
    const choice = items[Math.floor(Math.random() * items.length)];
    return choice;
  }

  setChoice() {
    this.setState({ currentChoice: this.pickChoice() });
  }

  render() {
    async function enviarCorreo(currentChoice, premioId) {
      try {
        let res = await fetch(
          `/api/puntos/sortear?idCliente=${currentChoice}&idConcepto=${premioId}`
        );
        //regla = await res.json();
      } catch (error) {
        console.log("ERROR; ", error);
      }
    }
    const { isRunning, currentChoice } = this.state;
    const { participantes, premioId } = this.props;
    const hasChoice = currentChoice;

    if (!isRunning && hasChoice) {
      enviarCorreo(currentChoice, premioId);
    }

    return (
      <div className="RandomPicker">
        <RandomPickerChoice
          choice={currentChoice}
          participantes={participantes}
        />
        <RandomPickerControls
          isRunning={isRunning}
          hasChoice={hasChoice}
          start={this.start}
          stop={this.stop}
          reset={this.reset}
          onCancel={this.props.onCancel}
          premioId={this.props.premioId}
        />
      </div>
    );
  }
}

RandomPicker.propTypes = {
  items: PropTypes.array,
  duration: PropTypes.number,
};

class RandomPickerChoice extends React.PureComponent {
  render() {
    const { choice, participantes } = this.props;
    const content = choice
      ? participantes.filter((p) => p.id == choice)
      : [{ nombre: "?" }];
    return (
      <div className="flex flex-col gap-4 items-center justify-center py-8">
        <span className="text-white text-xl">Ganador</span>
        <span className="text-white text-xl">{content[0].nombre}</span>
      </div>
    );
  }
}

RandomPickerChoice.propTypes = {
  choice: PropTypes.string,
};

class RandomPickerControls extends React.PureComponent {
  render() {
    const { isRunning, hasChoice, start, stop, reset, onCancel, premioId } =
      this.props;

    return (
      <div className="flex flex-row gap-4 justify-center">
        <button
          className={`${isRunning && "bg-red-500"} `}
          onClick={isRunning ? stop : start}
          disabled={!premioId}
        >
          {isRunning ? "Detener" : "Sortear"}
        </button>
        <button
          disabled={isRunning || !hasChoice}
          /*           class="RandomPicker__button RandomPicker__button--reset"
           */ onClick={reset}
        >
          Resetear
        </button>

        <button type="button" onClick={onCancel}>
          Cancelar
        </button>
      </div>
    );
  }
}

RandomPickerControls.propTypes = {
  isRunning: PropTypes.bool,
  hasChoice: PropTypes.bool,
  start: PropTypes.func,
  stop: PropTypes.func,
  reset: PropTypes.func,
};

export default RandomPicker;
