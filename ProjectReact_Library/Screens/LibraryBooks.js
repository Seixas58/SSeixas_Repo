import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import BookList from '../Components/BookList';
import ButtonGetLibraries from '../Components/ButtonGetLibraries';
import LibraryList from '../Components/LibraryList';
import LibraryBookList from '../Components/LibraryBooks/LibraryBooksList';

const LibraryBooks = () => {
  return (
    <View>
      <LibraryBookList />
    </View>
  );
};

export default LibraryBooks;
