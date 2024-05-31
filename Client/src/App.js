import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './pages/users/HomePage';
import Category from './pages/users/Category';
import Navigation from './Navigation';

function App() {
  return (
    <Router>
      <div>
        <Navigation />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/Category" component={Category} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;