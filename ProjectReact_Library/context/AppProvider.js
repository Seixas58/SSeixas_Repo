import React, {useReducer} from 'react';
import PropTypes from 'prop-types';
import {Provider} from './AppContext';
import reducer from './Reducer';

const initialState = {
  books: {
    loading: true,
    error: null,
    data: [],
  },
  libraries: {
    loading: true,
    error: null,
    data: [],
  },
  librariesBooks: {
    loading: true,
    error: null,
    data: [],
  },
  libraryId: '',
};

const AppProvider = props => {
  const [state, dispatch] = useReducer(reducer, initialState);
  return (
    <Provider
      value={{
        state,
        dispatch,
      }}>
      {props.children}
    </Provider>
  );
};
AppProvider.propTypes = {
  children: PropTypes.node,
};

export default AppProvider;
