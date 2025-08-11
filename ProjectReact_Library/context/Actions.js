import {makeHTTPRequest} from '../Service/Service';

export const URL_API = 'http://193.136.62.24/v1';

export const FETCH_BOOKS_STARTED = 'FETCH_BOOKS_STARTED';
export const FETCH_BOOKS_SUCCESS = 'FETCH_BOOKS_SUCCESS';
export const FETCH_BOOKS_FAILURE = 'FETCH_BOOKS_FAILURE';

export const FETCH_LIBRARIES_BOOKS_STARTED = 'FETCH_LIBRARIES_BOOKS_STARTED';
export const FETCH_LIBRARIES_BOOKS_SUCCESS = 'FETCH_LIBRARIES_BOOKS_SUCCESS';
export const FETCH_LIBRARIES_BOOKS_FAILURE = 'FETCH_LIBRARIES_BOOKS_FAILURE';


export const CHANGE_LIBRARY_ID = 'CHANGE_LIBRARY_ID';

export const FETCH_LIBRARIES_STARTED = 'FETCH_LIBRARIES_STARTED';
export const FETCH_LIBRARIES_SUCCESS = 'FETCH_LIBRARIES_SUCCESS';
export const FETCH_LIBRARIES_FAILURE = 'FETCH_LIBRARIES_FAILURE';
export function fetchBooks(url, request, dispatch) {
  //função ser executado em caso de sucesso
  const success = res => dispatch(fetchBooksSuccess(res));
  //função ser executado em caso de falha
  const failure = err => dispatch(fetchBooksFailure(err.message));
  makeHTTPRequest(url, request, success, failure);
}

export function fetchLibrariesBooks(url, request, dispatch) {
  //função ser executado em caso de sucesso
  const success = res => dispatch(fetchLibrariesBooksSuccess(res));
  //função ser executado em caso de falha
  const failure = err => dispatch(fetchLibrariesBooksFailure(err.message));
  makeHTTPRequest(url, request, success, failure);
}

export function fetchLibrariesBooksStarted() {
  return {
    type: FETCH_LIBRARIES_BOOKS_STARTED,
  };
}

export function fetchLibrariesBooksSuccess(books) {
  return {
    type: FETCH_LIBRARIES_BOOKS_SUCCESS,
    payload: {
      data: [...books],
    },
  };
}
export function fetchLibrariesBooksFailure(message) {
  return {
    type: FETCH_LIBRARIES_BOOKS_FAILURE,
    payload: {
      error: message,
    },
  };
}

export function fetchLibraries(url, request, dispatch) {
  //função ser executado em caso de sucesso
  const success = res => dispatch(fetchLibrariesSuccess(res));
  //função ser executado em caso de falha
  const failure = err => dispatch(fetchLibrariesFailure(err.message));
  makeHTTPRequest(url, request, success, failure);
}

export function changeLibraryId(libraryId) {
  return {
    type: CHANGE_LIBRARY_ID,
    payload: {
      data: libraryId,
    },
  };
}

export function fetchBooksStarted() {
  return {
    type: FETCH_BOOKS_STARTED,
  };
}

export function fetchBooksSuccess(books) {
  return {
    type: FETCH_BOOKS_SUCCESS,
    payload: {
      data: [...books],
    },
  };
}
export function fetchBooksFailure(message) {
  return {
    type: FETCH_BOOKS_FAILURE,
    payload: {
      error: message,
    },
  };
}

export function fetchLibrariesStarted() {
  return {
    type: FETCH_LIBRARIES_STARTED,
  };
}

export function fetchLibrariesSuccess(libraries) {
  return {
    type: FETCH_LIBRARIES_SUCCESS,
    payload: {
      data: [...libraries],
    },
  };
}
export function fetchLibrariesFailure(message) {
  return {
    type: FETCH_LIBRARIES_FAILURE,
    payload: {
      error: message,
    },
  };
}
