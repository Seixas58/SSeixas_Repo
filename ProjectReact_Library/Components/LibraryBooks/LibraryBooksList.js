import React, { useContext, useEffect } from 'react';
import AppContext from '../../context/AppContext';
import { Text, View, StyleSheet, FlatList } from 'react-native';
import {
  fetchBooksStarted,
  fetchLibrariesBooks,
  URL_API,
} from '../../context/Actions';
import LibraryBookListItem from './LibraryBooksListItem';
import { useRoute } from '@react-navigation/native';

const LibraryBookList = () => {
  const route = useRoute();
  const { libraryId } = route.params || {};
  const { state, dispatch } = useContext(AppContext);
  const { librariesBooks } = state;
  const { loading, error, data } = librariesBooks;

  useEffect(() => {
    if (libraryId) {
      console.log('Library ID:', libraryId);
      const url = `${URL_API}/library/${libraryId}/book`;
      console.log('Fetch URL:', url);
      dispatch(fetchBooksStarted());
      const request = {};
      fetchLibrariesBooks(url, request, dispatch);
    } else {
      console.log('No Library ID found');
    }
  }, [dispatch, libraryId]);

  if (loading === true) {
    return (
        <View style={styles.item}>
          <Text>Loading ....</Text>
        </View>
    );
  } else if (error !== null) {
    return (
        <View style={styles.item}>
          <Text>Error ....</Text>
        </View>
    );
  } else if (data.length > 0) {
    return (
        <FlatList
            data={data}
            renderItem={({ item }) => {
              if (!item.book || !item.book.isbn) {
                console.warn("Missing book data or ISBN:", item);
                return null;
              }
              return <LibraryBookListItem item={item} libraryId={libraryId} />;
            }}
            keyExtractor={(item) => item.book.isbn}
        />

    );
  } else {
    return (
        <View style={styles.item}>
          <Text>No data ....</Text>
        </View>
    );
  }
};

const styles = StyleSheet.create({
  item: {
    borderTopWidth: 1,
    borderBottomWidth: 1,
    borderColor: '#E0E0E0',
    backgroundColor: '#FFFFFF',
    padding: 15,
    marginVertical: 10,
    borderRadius: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
});

export default LibraryBookList;
