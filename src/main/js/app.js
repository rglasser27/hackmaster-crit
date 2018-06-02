src/main/js/app.js

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {response: {}};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/hackmaster'}).done(response => {
			this.setState({lookupResponse: response.entity._embedded.lookupResponse});
		});
	}

	render() {
		return (
		    <div>Hello world</div>
			<LookupResponse lookupResponse={this.state.lookupResponse}/>
		)
	}
}
