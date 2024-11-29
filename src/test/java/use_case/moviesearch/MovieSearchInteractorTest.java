package use_case.moviesearch;


//class MovieSearchInteractorTest {
//    @Test
//    void successTest() {
//        MovieSearchInputData inputData = new MovieSearchInputData("Harry Potter", "None", "None", new List<String>() {
//            @Override
//            public int size() {
//                return 0;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @Override
//            public boolean contains(Object o) {
//                return false;
//            }
//
//            @NotNull
//            @Override
//            public Iterator<String> iterator() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public Object[] toArray() {
//                return new Object[0];
//            }
//
//            @NotNull
//            @Override
//            public <T> T[] toArray(@NotNull T[] a) {
//                return null;
//            }
//
//            @Override
//            public boolean add(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean remove(Object o) {
//                return false;
//            }
//
//            @Override
//            public boolean containsAll(@NotNull Collection<?> c) {
//                return false;
//            }
//
//            @Override
//            public boolean addAll(@NotNull Collection<? extends String> c) {
//                return false;
//            }
//
//            @Override
//            public boolean addAll(int index, @NotNull Collection<? extends String> c) {
//                return false;
//            }
//
//            @Override
//            public boolean removeAll(@NotNull Collection<?> c) {
//                return false;
//            }
//
//            @Override
//            public boolean retainAll(@NotNull Collection<?> c) {
//                return false;
//            }
//
//            @Override
//            public void clear() {
//
//            }
//
//            @Override
//            public String get(int index) {
//                return "";
//            }
//
//            @Override
//            public String set(int index, String element) {
//                return "";
//            }
//
//            @Override
//            public void add(int index, String element) {
//
//            }
//
//            @Override
//            public String remove(int index) {
//                return "";
//            }
//
//            @Override
//            public int indexOf(Object o) {
//                return 0;
//            }
//
//            @Override
//            public int lastIndexOf(Object o) {
//                return 0;
//            }
//
//            @NotNull
//            @Override
//            public ListIterator<String> listIterator() {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public ListIterator<String> listIterator(int index) {
//                return null;
//            }
//
//            @NotNull
//            @Override
//            public List<String> subList(int fromIndex, int toIndex) {
//                return List.of();
//            }
//        });
//        MovieSearchDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
//
//        // This creates a successPresenter that tests whether the test case is as we expect.
//        MovieSearchOutputBoundary successPresenter = new MovieSearchOutputBoundary() {
//            @Override
//            public void prepareSuccessView(MovieSearchOutputData moviesList) {
//                Assertions.assertEquals("Harry Potter and the Philosopher's Stone", moviesList.getMovies().get(0).getTitle());
//            }
//
//            @Override
//            public void prepareFailView(String error) {
//                Assertions.fail("Use case failure is unexpected.");
//            }
//        };
//
//        MovieSearchInputBoundary interactor = new MovieSearchInteractor(tmdbDataAccessObject, successPresenter);
//        interactor.execute(inputData);
//    }
//}
