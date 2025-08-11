import {
  FETCH_BOOKS_STARTED,
  FETCH_BOOKS_SUCCESS,
  FETCH_BOOKS_FAILURE,
  FETCH_LIBRARIES_STARTED,
  FETCH_LIBRARIES_SUCCESS,
  FETCH_LIBRARIES_FAILURE,
  CHANGE_LIBRARY_ID,
  FETCH_LIBRARIES_BOOKS_STARTED,
  FETCH_LIBRARIES_BOOKS_SUCCESS,
  FETCH_LIBRARIES_BOOKS_FAILURE,
} from './Actions';

function reducer(state, action) {
  switch (action.type) {
    case FETCH_BOOKS_STARTED:
      return {
        ...state,
        books: {
          loading: true,
          error: null,
          data: [],
        },
      };
    case FETCH_BOOKS_SUCCESS:
      return {
        ...state,
        books: {
          loading: false,
          error: null,
          data: [...action.payload.data],
        },
      };
    case FETCH_LIBRARIES_BOOKS_FAILURE:
      return {
        ...state,
        librariesBooks: {
          loading: false,
          error: action.payload.error,
          data: [],
        },
      };
    case FETCH_LIBRARIES_BOOKS_STARTED:
      return {
        ...state,
        librariesBooks: {
          loading: true,
          error: null,
          data: [],
        },
      };
    case FETCH_LIBRARIES_BOOKS_SUCCESS:
      return {
        ...state,
        librariesBooks: {
          loading: false,
          error: null,
          data: [...action.payload.data],
        },
      };
    case FETCH_BOOKS_FAILURE:
      return {
        ...state,
        books: {
          loading: false,
          error: action.payload.error,
          data: [],
        },
      };
    case FETCH_LIBRARIES_STARTED:
      return {
        ...state,
        libraries: {
          loading: true,
          error: null,
          data: [],
        },
      };
    case FETCH_LIBRARIES_SUCCESS:
      return {
        ...state,
        libraries: {
          loading: false,
          error: null,
          data: [...action.payload.data],
        },
      };
    case FETCH_LIBRARIES_FAILURE:
      return {
        ...state,
        libraries: {
          loading: false,
          error: action.payload.error,
          data: [],
        },
      };
    case CHANGE_LIBRARY_ID:
      return {
        ...state,
        libraryId: action.payload.data,
      };
    default:
      return state;
  }
}

export default reducer;
