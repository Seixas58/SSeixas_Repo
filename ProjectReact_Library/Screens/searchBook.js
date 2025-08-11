import React from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import SearchBar from '../Components/SearchBar';
import BookList from '../Components/BookList';

const SearchBook = () => {
  return (
      <View style={styles.container}>
        <Text style={styles.header}>Pesquisar Livros</Text>
        <SearchBar />
        <ScrollView contentContainerStyle={styles.bookListContainer}>
          <BookList />
        </ScrollView>
      </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    backgroundColor: '#f5f5f5',
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333',
    marginBottom: 16,
    textAlign: 'center',
  },
  bookListContainer: {
    marginTop: 16,
    paddingBottom: 16,
  },
});

export default SearchBook;
